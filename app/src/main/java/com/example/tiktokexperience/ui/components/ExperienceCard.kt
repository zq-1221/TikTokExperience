package com.example.tiktokexperience.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tiktokexperience.model.Experience
import coil.compose.SubcomposeAsyncImage

@Composable
fun ExperienceCard(
    experience: Experience,
    onLikeClicked: (Experience) -> Unit,
    modifier: Modifier = Modifier
) {
    var liked by remember { mutableStateOf(experience.isLiked) }
    var likeCount by remember { mutableStateOf(experience.likes) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            // 图片区域 - 使用Coil加载网络图片
            SubcomposeAsyncImage(
                model = experience.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(experience.contentHeight.dp)
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                contentScale = ContentScale.Crop,
                loading = {
                    // 加载时的占位符
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(32.dp))
                    }
                },
                error = {
                    // 加载失败时的占位符
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.errorContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "图片加载失败",
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            // 标题
            Text(
                text = experience.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(horizontal = 12.dp),
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 用户信息和点赞
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp)
                    .padding(bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // 用户信息
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 用户头像 - 使用Coil加载
                    SubcomposeAsyncImage(
                        model = experience.userAvatar,
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        loading = {
                            // 加载时的占位符
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(MaterialTheme.colorScheme.secondary)
                            )
                        },
                        error = {
                            // 加载失败时的占位符
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(MaterialTheme.colorScheme.secondary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = experience.userName.first().toString(),
                                    color = MaterialTheme.colorScheme.onSecondary
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // 用户名
                    Text(
                        text = experience.userName,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // 点赞按钮
                IconButton(
                    onClick = {
                        liked = !liked
                        likeCount = if (liked) likeCount + 1 else likeCount - 1
                        onLikeClicked(experience.copy(isLiked = liked, likes = likeCount))
                    },
                    modifier = Modifier.size(40.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (liked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = if (liked) "取消点赞" else "点赞",
                            tint = if (liked) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = if (likeCount > 999) "${likeCount / 1000}k" else likeCount.toString(),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 14.sp
                            ),
                            color = if (liked) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}