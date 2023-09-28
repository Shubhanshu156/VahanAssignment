package com.example.vahanassignment.data

import android.util.Log
import com.example.vahanassignment.Database.UniversityDatabase
import com.example.vahanassignment.Database.UniversityItem
import com.example.vahanassignment.network.ApiService
import com.example.vahanassignment.network.models.UniversityResponseItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UniversityRepository @Inject constructor(
    private val api: ApiService,
    private val db: UniversityDatabase
) {
    private var _universitylist =
        MutableStateFlow(listOf<UniversityItem>());
    val universitylist = _universitylist.asStateFlow()
    suspend fun getUniversityList() {
        try {
            val resultFromApi = api.getUniversityList()

            if (resultFromApi.isSuccessful) {
                val responseBody = resultFromApi.body()
                if (responseBody != null) {
                    Log.d("UniversityRepository", "Fetching data from network: $responseBody")

                    val universities = responseBody.map { it.toUniversityItem() }
                    val dao = db.getFeedDao()
                    val existingItems = dao.getUniversityList()
                    val itemsToDelete = findItemsToDelete(existingItems, universities)

                    // Delete items that exist in the database but not in the new formatted list
                    itemsToDelete.forEach { dao.delete(it) }

                    // Insert new formatted items into the database
                    dao.insertall(universities)
                } else {
                    Log.e("UniversityRepository", "Response body is null")
                }
            } else {
                Log.e("UniversityRepository", "Response not successful: ${resultFromApi.code()}")
            }
        } catch (e: HttpException) {
            handleHttpException(e)
        } catch (e: IOException) {
            handleIoException(e)
        } finally {
            val universities = db.getFeedDao().getUniversityList()
            Log.d("size of res is", "getUniversityList: ${universities.size}")
            val start = (0..10).random()
            val end = (start + 10..start + 20).random()
            _universitylist.value = universities.subList(start, end.coerceAtMost(universities.size))
            Log.d("Inside repository", "getUniversityList: ${_universitylist.value}")
        }
    }

    private fun UniversityResponseItem.toUniversityItem(): UniversityItem {
        return UniversityItem(
            country = this.country,
            name = this.name,
            web_pages = this.web_pages[0]
        )
    }

    private fun findItemsToDelete(
        existingItems: List<UniversityItem>,
        newItems: List<UniversityItem>
    ): List<UniversityItem> {
        return existingItems.filter { existingItem ->
            !newItems.any { newItem ->
                newItem.country == existingItem.country &&
                        newItem.name == existingItem.name &&
                        newItem.web_pages == existingItem.web_pages
            }
        }
    }

    private fun handleHttpException(e: HttpException) {
        Log.e("UniversityRepository", "HTTP Exception: ${e.message()}", e)
        throw Exception("Oops, something went wrong!")
    }

    private fun handleIoException(e: IOException) {
        Log.e("UniversityRepository", "IO Exception: ${e.message}", e)
        throw Exception("Couldn't reach the server, check your internet connection.")
    }
    private suspend fun findItemDB(){

    }

}
