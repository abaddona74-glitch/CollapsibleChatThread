#!/usr/bin/env python3
import os
import shutil
import datetime

ROOT = os.path.abspath(os.path.join(os.path.dirname(__file__), '..'))
EXCLUDE_DIRS = {'build', '.gradle', '.git', 'backups', 'gradle', 'app\\build'}
EXTS = ('.kt', '.java')

now = datetime.datetime.now().strftime('%Y%m%d_%H%M%S')
BACKUP_DIR = os.path.join(ROOT, 'backups', f'code_comments_backup_{now}')

print('Root:', ROOT)
print('Creating backup at', BACKUP_DIR)
os.makedirs(BACKUP_DIR, exist_ok=True)

files = []
for dirpath, dirnames, filenames in os.walk(ROOT):
    # Skip excluded directories
    rel = os.path.relpath(dirpath, ROOT)
    parts = rel.split(os.sep)
    if any(p in EXCLUDE_DIRS for p in parts if p != '.'):
        continue
    for fn in filenames:
        if fn.endswith(EXTS):
            fp = os.path.join(dirpath, fn)
            files.append(fp)

print(f'Found {len(files)} files to process')

# copy to backup preserving relative paths
for f in files:
    rel = os.path.relpath(f, ROOT)
    dest = os.path.join(BACKUP_DIR, rel)
    os.makedirs(os.path.dirname(dest), exist_ok=True)
    shutil.copy2(f, dest)

print('Backups created.')

# function to strip comments while preserving strings

def strip_comments(code: str) -> str:
    out = []
    i = 0
    n = len(code)
    in_s = False  # double-quote string
    in_c = False  # single-quote char/string
    in_sl = False  # single-line comment
    in_ml = False  # multi-line comment
    esc = False
    while i < n:
        ch = code[i]
        nxt = code[i+1] if i+1 < n else ''
        if in_sl:
            if ch == '\n':
                in_sl = False
                out.append(ch)
            # else skip
            i += 1
            continue
        if in_ml:
            if ch == '*' and nxt == '/':
                in_ml = False
                i += 2
            else:
                i += 1
            continue
        if in_s:
            out.append(ch)
            if ch == '\\' and not esc:
                esc = True
            elif ch == '"' and not esc:
                in_s = False
            else:
                esc = False
            i += 1
            continue
        if in_c:
            out.append(ch)
            if ch == '\\' and not esc:
                esc = True
            elif ch == "'" and not esc:
                in_c = False
            else:
                esc = False
            i += 1
            continue
        # not in any state
        if ch == '/' and nxt == '/':
            in_sl = True
            i += 2
            continue
        if ch == '/' and nxt == '*':
            in_ml = True
            i += 2
            continue
        if ch == '"':
            in_s = True
            out.append(ch)
            i += 1
            continue
        if ch == "'":
            in_c = True
            out.append(ch)
            i += 1
            continue
        out.append(ch)
        i += 1
    return ''.join(out)

# process files
changed = 0
for f in files:
    with open(f, 'r', encoding='utf-8') as fh:
        src = fh.read()
    stripped = strip_comments(src)
    if stripped != src:
        with open(f, 'w', encoding='utf-8') as fh:
            fh.write(stripped)
        changed += 1

print(f'Files changed: {changed}')
print('Done. If build fails, you can restore backups from', BACKUP_DIR)
