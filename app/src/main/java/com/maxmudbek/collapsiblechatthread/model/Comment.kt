package com.maxmudbek.collapsiblechatthread.model


data class Comment(
    val id: String,
    val authorName: String,
    val authorInitial: String,
    val timestamp: Long,
    val channel: String? = null,  
    val title: String? = null,     
    val tags: List<String> = emptyList(),
    val content: String,
    val replies: List<Comment> = emptyList(),
    val isExpanded: Boolean = true  
) {
    
    fun getTotalReplyCount(): Int {
        var count = replies.size
        replies.forEach { reply ->
            count += reply.getTotalReplyCount()
        }
        return count
    }
    
    
    fun getDirectReplyCount(): Int = replies.size
    
    
    fun hasReplies(): Boolean = replies.isNotEmpty()
}
