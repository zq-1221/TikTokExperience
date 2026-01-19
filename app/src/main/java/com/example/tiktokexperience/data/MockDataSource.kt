package com.example.tiktokexperience.data

import com.example.tiktokexperience.model.Experience

object MockDataSource {
    // 使用更可靠的图片URL
    private val mockImages = listOf(
        "https://images.unsplash.com/photo-1506744038136-46273834b3fb?w=300&h=200&fit=crop",
        "https://images.unsplash.com/photo-1519681393784-d120267933ba?w=300&h=250&fit=crop",
        "https://images.unsplash.com/photo-1501785888041-af3ef285b470?w=300&h=300&fit=crop",
        "https://images.unsplash.com/photo-1470071459604-3b5ec3a7fe05?w=300&h=350&fit=crop",
        "https://images.unsplash.com/photo-1469474968028-56623f02e42e?w=300&h=400&fit=crop",
        "https://images.unsplash.com/photo-1501854140801-50d01698950b?w=300&h=450&fit=crop",
        "https://images.unsplash.com/photo-1441974231531-c6227db76b6e?w=300&h=500&fit=crop",
        "https://images.unsplash.com/photo-1439853949127-fa647821eba0?w=300&h=550&fit=crop",
    )

    private val mockAvatars = listOf(
        "https://images.unsplash.com/photo-1494790108755-2616b612b786?w=50&h=50&fit=crop",
        "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=50&h=50&fit=crop",
        "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=50&h=50&fit=crop",
        "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=50&h=50&fit=crop",
        "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?w=50&h=50&fit=crop",
    )

    private val mockNames = listOf(
        "张三", "李四", "王五", "赵六", "孙七",
        "周八", "吴九", "郑十", "钱多多", "孙小小"
    )

    private val mockTitles = listOf(
        "今天学会了做蛋炒饭，超级好吃！",
        "分享一个提高效率的小技巧",
        "周末去爬山，风景太美了",
        "编程经验分享：如何优化代码",
        "健身打卡第30天，感觉更健康了",
        "读书心得：《人类简史》",
        "旅行攻略：云南7日游",
        "美食探店：这家火锅店真的绝了",
        "学习Python的5个实用技巧",
        "如何保持每天早起的习惯"
    )

    fun getMockExperiences(count: Int = 20): List<Experience> {
        return List(count) { index ->
            Experience(
                id = "exp_$index",
                title = mockTitles[index % mockTitles.size],
                imageUrl = mockImages[index % mockImages.size],
                userAvatar = mockAvatars[index % mockAvatars.size],
                userName = mockNames[index % mockNames.size],
                likes = (100..1000).random(),
                isLiked = false,
                contentHeight = (200..400).random()
            )
        }
    }

    fun getMoreExperiences(startIndex: Int, count: Int = 10): List<Experience> {
        return getMockExperiences(count).mapIndexed { index, experience ->
            experience.copy(
                id = "exp_${startIndex + index}",
                title = mockTitles[(startIndex + index) % mockTitles.size]
            )
        }
    }
}