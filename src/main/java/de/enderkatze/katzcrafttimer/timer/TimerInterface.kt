package de.enderkatze.katzcrafttimer.timer

interface TimerInterface {
    var running: Boolean;
    var paused: Boolean;
    var displayTimeOnPause: Boolean;

    var time: Int;
}