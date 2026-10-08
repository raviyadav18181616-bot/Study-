package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.engine.TestBlueprintEngine
import com.example.data.seed.BiharBoardCurriculumData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("StudyPath", appName)
    }

    @Test
    fun `verify bihar board curriculum data loaded`() {
        val maths = BiharBoardCurriculumData.mathsChapters
        assertEquals(15, maths.size)

        val science = BiharBoardCurriculumData.scienceChapters
        assertTrue(science.size >= 16)

        val sst = BiharBoardCurriculumData.sstChapters
        assertTrue(sst.size >= 25)

        val hindi = BiharBoardCurriculumData.hindiChapters
        assertTrue(hindi.size >= 20)
    }

    @Test
    fun `verify one-click test generation`() {
        val (config, questions) = TestBlueprintEngine.createOneClickChapterTest("m_01")
        assertEquals(10, config.questionCount)
        assertTrue(questions.isNotEmpty())
    }
}
