package com.fk.codesample.mappers

import com.fk.codesample.entities.Cat
import com.fk.codesample.network.dto.BreedDto
import com.fk.codesample.network.dto.CatDto
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class CatDtoMapperTest {

   private  val catDtoMapper = CatDtoMapper()

    @Test
    fun `returns null when breed is null`() {
        assertNull(catDtoMapper.map(CatDto("id", "url", null)))
    }

    @Test
    fun `returns null when breed is empty list`() {
        assertNull(catDtoMapper.map(CatDto("id", "url", emptyList())))
    }

    @Test
    fun `returns null when breed name is null`() {
        assertNull(
            catDtoMapper.map(
                CatDto(
                    "id",
                    "url",
                    listOf(BreedDto("id", null, "temperament", "origin"))
                )
            )
        )
    }

    @Test
    fun `returns null when breed temperament is null`() {
        assertNull(
            catDtoMapper.map(
                CatDto(
                    "id",
                    "url",
                    listOf(BreedDto("id", "name", null, "origin"))
                )
            )
        )
    }

    @Test
    fun `returns null when breed origin is null`() {
        assertNull(
            catDtoMapper.map(
                CatDto(
                    "id",
                    "url",
                    listOf(BreedDto("id", "name", "temperament", null))
                )
            )
        )
    }

    @Test
    fun `returns null when id is null`() {
        assertNull(
            catDtoMapper.map(
                CatDto(
                    null,
                    "url",
                    listOf(BreedDto("id", "name", "temperament", "origin"))
                )
            )
        )
    }

    @Test
    fun `returns null when url is null`() {
        assertNull(
            catDtoMapper.map(
                CatDto(
                    "id",
                    null,
                    listOf(BreedDto("id", "name", "temperament", "origin"))
                )
            )
        )
    }

    @Test
    fun `returns expected result when data is present`() {
        val expectedCat = Cat("id", "imageUrl", "breedName", "breedTemperament", "origin")
        assertEquals( expectedCat,
            catDtoMapper.map(
                CatDto(
                    expectedCat.id,
                    expectedCat.imageUrl,
                    listOf(BreedDto(null, expectedCat.breedName, expectedCat.breedTemperament, expectedCat.origin))
                )
            )
        )
    }
}