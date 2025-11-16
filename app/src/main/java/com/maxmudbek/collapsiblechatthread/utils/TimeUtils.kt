package com.maxmudbek.collapsiblechatthread.utils

/**
 * Utility functions for time formatting
 */
object TimeUtils {
    
    /**
     * Converts a timestamp to relative time format
     * Examples: "5 minutes ago", "2 hours ago", "1 day ago"
     * 
     * @param timestamp Unix timestamp in milliseconds
     * @return Formatted relative time string
     */
    fun getRelativeTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp
        
        val seconds = diff / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7
        val months = days / 30
        val years = days / 365
        
        return when {
            seconds < 60 -> "just now"
            minutes < 60 -> if (minutes == 1L) "1 minute ago" else "$minutes minutes ago"
            hours < 24 -> if (hours == 1L) "1 hour ago" else "$hours hours ago"
            days < 7 -> if (days == 1L) "1 day ago" else "$days days ago"
            weeks < 4 -> if (weeks == 1L) "1 week ago" else "$weeks weeks ago"
            months < 12 -> if (months == 1L) "1 month ago" else "$months months ago"
            else -> if (years == 1L) "1 year ago" else "$years years ago"
        }
    }
}
