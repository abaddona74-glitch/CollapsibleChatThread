package com.maxmudbek.collapsiblechatthread.model

/**
 * Data class representing a single comment in the thread.
 * Supports infinite nesting through the replies list.
 * 
 * @param id Unique identifier for the comment
 * @param authorName Full name of the comment author
 * @param authorInitial First letter of author name for profile icon
 * @param timestamp Unix timestamp when comment was posted
 * @param channel Chat channel (only for root comments) e.g. "/ProductReflection"
 * @param title Title text (only for root comments)
 * @param tags List of author badges like "Top 1% Commenter"
 * @param content The actual message content
 * @param replies List of nested reply comments
 * @param isExpanded State to track if replies are visible or collapsed
 */
data class Comment(
    val id: String,
    val authorName: String,
    val authorInitial: String,
    val timestamp: Long,
    val channel: String? = null,  // Only for root level comments
    val title: String? = null,     // Only for root level comments
    val tags: List<String> = emptyList(),
    val content: String,
    val replies: List<Comment> = emptyList(),
    val isExpanded: Boolean = true  // Default to expanded state
) {
    /**
     * Returns total count of all nested replies recursively
     */
    fun getTotalReplyCount(): Int {
        var count = replies.size
        replies.forEach { reply ->
            count += reply.getTotalReplyCount()
        }
        return count
    }
    
    /**
     * Returns direct reply count (only immediate children)
     */
    fun getDirectReplyCount(): Int = replies.size
    
    /**
     * Checks if this comment has any replies
     */
    fun hasReplies(): Boolean = replies.isNotEmpty()
}
