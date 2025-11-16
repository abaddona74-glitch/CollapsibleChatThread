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
                        timestamp = now - (oneDay - 2 * oneHour),
                        tags = listOf("Top 1% Commenter"),
                        content = "Really insightful — I ran into similar issues with feature creep. Have you considered an MVP with A/B tests?"
                    ),
                    Comment(
                        id = "1-2",
                        authorName = "CodeNamedQuiet",
                        authorInitial = "C",
                        timestamp = now - (oneDay - 3 * oneHour),
                        content = "Thanks for sharing. Marketing is often overlooked; did you try community-driven launches?"
                    ),
                    Comment(
                        id = "1-3",
                        authorName = "InfiniteCoffeeStream",
                        authorInitial = "I",
                        timestamp = now - (oneDay - 4 * oneHour),
                        content = "The timeline resonates — burnout is real. Curious how you prioritized features."
                    )
                )
            )
        )
    }
}
