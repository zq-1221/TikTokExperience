package com.example.tiktokexperience.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.example.tiktokexperience.model.Experience
import com.example.tiktokexperience.ui.components.ExperienceCard
import kotlinx.coroutines.delay
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExperienceScreen() {
    var experiences by remember { mutableStateOf(emptyList<Experience>()) }
    var isLoading by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }
    var currentPage by remember { mutableStateOf(0) }
    val itemsPerPage = 10
    val coroutineScope = rememberCoroutineScope()

    // 初始化数据
    LaunchedEffect(Unit) {
        loadInitialData(experiences) { loadedData ->
            experiences = loadedData
        }
    }

    // 下拉刷新状态
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isRefreshing)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "经验频道",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            isRefreshing = true
                            coroutineScope.launch {
                                loadInitialData(emptyList()) { loadedData ->
                                    experiences = loadedData
                                    currentPage = 0
                                    isRefreshing = false
                                }
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Refresh, contentDescription = "刷新")
                    }
                }
            )
        }
    ) { paddingValues ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                isRefreshing = true
                coroutineScope.launch {
                    loadInitialData(emptyList()) { loadedData ->
                        experiences = loadedData
                        currentPage = 0
                        isRefreshing = false
                    }
                }
            },
            modifier = Modifier.padding(paddingValues)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = experiences,
                    key = { it.id }
                ) { experience ->
                    ExperienceCard(
                        experience = experience,
                        onLikeClicked = { updatedExperience ->
                            val index = experiences.indexOfFirst { it.id == updatedExperience.id }
                            if (index != -1) {
                                experiences = experiences.toMutableList().apply {
                                    set(index, updatedExperience)
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // 加载更多项
                item {
                    if (isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            TextButton(
                                onClick = {
                                    isLoading = true
                                    coroutineScope.launch {
                                        loadMoreData(currentPage, itemsPerPage) { newExperiences ->
                                            experiences = experiences + newExperiences
                                            currentPage++
                                            isLoading = false
                                        }
                                    }
                                }
                            ) {
                                Text("加载更多")
                            }
                        }
                    }
                }
            }
        }
    }
}

// 加载初始数据
private suspend fun loadInitialData(
    currentData: List<Experience>,
    onDataLoaded: (List<Experience>) -> Unit
) {
    delay(1000) // 模拟网络延迟
    val mockData = com.example.tiktokexperience.data.MockDataSource.getMockExperiences(20)
    onDataLoaded(mockData)
}

// 加载更多数据
private suspend fun loadMoreData(
    page: Int,
    count: Int,
    onDataLoaded: (List<Experience>) -> Unit
) {
    delay(1500) // 模拟网络延迟
    val startIndex = (page + 1) * count
    val moreData = com.example.tiktokexperience.data.MockDataSource.getMoreExperiences(startIndex, count)
    onDataLoaded(moreData)
}
