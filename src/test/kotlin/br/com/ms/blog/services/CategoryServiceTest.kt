package br.com.ms.blog.services

import br.com.ms.blog.errors.exception.EntityNotFoundException
import br.com.ms.blog.models.Category
import br.com.ms.blog.repositories.CategoryRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CategoryServiceTest {

    @InjectMocks
    private lateinit var categoryService: CategoryService

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @Mock
    private lateinit var postService: PostService

    @Test
    fun `given valid Category should update an existent Category`(){
        val category = Category("Category")
        val updatedCategory = Category("Category Updated")

        whenever(categoryRepository.findById(anyLong())).thenReturn(category)
        whenever(categoryRepository.save(any())).thenReturn(any())

        categoryService.update(0, updatedCategory)

        assertThat(category).isEqualToComparingFieldByField(updatedCategory)
    }

    @Test(expected = EntityNotFoundException::class)
    fun `given ID of non existent author should throw EntityNotFoundException`(){
        whenever(categoryRepository.findById(anyLong())).thenReturn(null)

        categoryService.findById(0)
    }
}