package de.enderkatze.katzcrafttimer.presenter

class TimeFormatter {

    companion object {
        fun getFormattedTime(timeValue: Int): String {
            var timeString = ""

            val seconds = timeValue % 60
            val minutes = (timeValue / 60) % 60
            val hours = timeValue / 60 / 60

            var secondsStr: String = seconds.toString()
            var minutesStr: String = minutes.toString()
            var hoursStr: String = hours.toString()

            if (seconds < 10) {
                secondsStr = "0$seconds"
            }
            if (minutes < 10) {
                minutesStr = "0$minutes"
            }
            if (hours < 10) {
                hoursStr = "0$hours"
            }

            timeString = "$hoursStr:$minutesStr:$secondsStr"

            return timeString
        }
    }
}