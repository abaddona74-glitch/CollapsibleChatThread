package com.maxmudbek.collapsiblechatthread.utils

import com.maxmudbek.collapsiblechatthread.model.Comment


object SampleData {
    
    
    fun getSampleComments(): List<Comment> {
        val now = System.currentTimeMillis()
        val oneMinute = 60 * 1000L
        val oneHour = 60 * oneMinute
        val oneDay = 24 * oneHour
        
        return listOf(
            
            Comment(
                id = "1",
                authorName = "DreamSyntaxHiker",
                authorInitial = "D",
                timestamp = now - oneDay,
                channel = "/ProductReflection",
                title = "I Tried to Build a Language-Learning App. It Didn't Go as Planned.",
                tags = emptyList(),
                content = """I spent 9 months building what I thought would be a simple app to help people learn new languages through short conversations. I poured my evenings and weekends into it, but the launch was... underwhelming.
Here's what I learned about feature creep, marketing missteps, and chasing perfection instead of feedback.""",
                replies = listOf(
                    Comment(
                        id = "1-1",
                        authorName = "ByteBuddy88",
                        authorInitial = "B",
                        timestamp = now - oneDay,
                        tags = listOf("Top 1% Commenter"),
                            content = "Can relate. I thought adding 3 extra modes would help my music app. Users just wanted a clean player.",
                            replies = listOf(
                                Comment(
                                    id = "1-1-1",
                                    authorName = "CodeNamedQuiet",
                                    authorInitial = "C",
                                    timestamp = now - oneDay,
                                    content = "Can relate. I thought adding 3 extra modes would help my music app. Users just wanted a clean player."
                                ),
                                Comment(
                                    id = "1-1-2",
                                    authorName = "InfiniteCoffee",
                                    authorInitial = "I",
                                    timestamp = now - oneDay,
                                    content = "A minimal core product > overloaded app."
                                ),
                                Comment(
                                    id = "1-1-3",
                                    authorName = "APITestDummy",
                                    authorInitial = "A",
                                    timestamp = now - oneDay,
                                    content = "Added offline support, 5 themes, and full emoji search. Users preferred smooth onboarding."
                                ),
                                Comment(
                                    id = "1-1-4",
                                    authorName = "RetroUXFlare",
                                    authorInitial = "R",
                                    timestamp = now - oneDay,
                                    content = "Too many features = too many bugs = bad reviews. Learned that the hard way."
                                )
                            )
                    ),
                    Comment(
                        id = "1-2",
                        authorName = "MemoStacker",
                        authorInitial = "M",
                        timestamp = now - oneDay,
                        tags = listOf("Top 1% Poster"),
                        content = """This is a timely post. Iʼm in month 4 of building a journaling app. Biggest takeaway so far: donʼt underestimate the effort to onboard first-time users.
I had people open the app, stare blankly, and close it.
- UI needs to guide the user, not just look good
- First use experience is everything
- Every extra step kills conversion"""
                    ),
                    Comment(
                        id = "1-3",
                        authorName = "CLIOverlord",
                        authorInitial = "C",
                        timestamp = now - oneDay,
                            content = "My failed app taught me more than my CS degree. There's no syllabus for building something strangers actually want.",
                            replies = listOf(
                                Comment(
                                    id = "1-3-1",
                                    authorName = "DebugDruid",
                                    authorInitial = "D",
                                    timestamp = now - oneDay,
                                    content = "Truth. The gap between \u201cthis is useful to me\u201d and \u201cthis is useful to others\u201d is massive."
                                ),
                                Comment(
                                    id = "1-3-2",
                                    authorName = "SilentLambda",
                                    authorInitial = "S",
                                    timestamp = now - oneDay,
                                    content = "You don't learn product-market fit in school. You learn it after shipping something no one wants"
                                )
                            )
                    )
                )
            )
        )
    }
}
