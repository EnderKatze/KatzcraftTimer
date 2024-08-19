package de.enderkatze.katzcrafttimer.timer

import de.enderkatze.katzcrafttimer.Main
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitScheduler
import org.bukkit.scheduler.BukkitTask
import org.junit.Assert.*
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.*


class TimerNormalTest {

    private lateinit var timerNormal: TimerNormal
    private val mockPlugin: Main = mock(Main::class.java)
    private val mockTimerManager: TimerManager = mock(TimerManager::class.java)
    private val mockTask: BukkitTask = mock(BukkitTask::class.java)
    private val mockScheduler: BukkitScheduler = mock(BukkitScheduler::class.java)

    @BeforeEach
    fun setUp() {

        reset(mockPlugin, mockTimerManager, mockTask)
        timerNormal = TimerNormal(mockPlugin, mockTimerManager)
        mockTimerManager.setPrimaryTimer(0)

        `when`(Bukkit.getScheduler()).thenReturn(mockScheduler)
    }

    @Test
    fun `reset should set time to 0 and mark as not running`() {
        timerNormal.time = 10
        timerNormal.start()

        timerNormal.time = 0

        Assertions.assertEquals(0, timerNormal.time)
        Assertions.assertFalse(timerNormal.isRunning())
    }

    @Test
    fun `getTime should return the current time`() {
        timerNormal.time = 42
        Assertions.assertEquals(42, timerNormal.time)
    }

    @Test
    fun `getId should return a non-null unique identifier`() {
        Assertions.assertNotNull(timerNormal.id)
    }

    @Test
    fun `start should schedule a timer task`() {
        `when`(mockScheduler.runTaskTimerAsynchronously(eq(mockPlugin), any(Runnable::class.java), anyLong(), anyLong())).thenReturn(mockTask)

        timerNormal.start()

        verify(mockScheduler).runTaskTimerAsynchronously(eq(mockPlugin), any(Runnable::class.java), eq(0L), eq(20L))
        assertTrue(timerNormal.isRunning())
    }

    @Test
    fun `stop should cancel the running task and stop the timer`() {
        `when`(mockScheduler.runTaskTimerAsynchronously(eq(mockPlugin), any(Runnable::class.java), anyLong(), anyLong())).thenReturn(mockTask)

        timerNormal.start()
        assertTrue(timerNormal.isRunning())

        timerNormal.stop()

        verify(mockTask).cancel()
        assertFalse(timerNormal.isRunning())
    }

    @Test
    fun `toMap should return a map with current timer state`() {
        timerNormal.time = 123
        timerNormal.start()

        val map = timerNormal.toMap()

        assertEquals(123, map["time"])
        assertTrue(map["running"] as Boolean)
        assertTrue(map["isPrimary"] as Boolean)
    }

    @Test
    fun `isPrimaryTimer should return true if this is the primary timer`() {
        `when`(mockTimerManager.getPrimaryTimer()).thenReturn(timerNormal)

        assertTrue(timerNormal.isPrimaryTimer())
    }
}