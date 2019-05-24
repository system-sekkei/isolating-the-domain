module Pages.TimeRecord.Rules.WorkTimeRangeRule exposing (WorkTimeRangeRule(..), validate)

import Pages.TimeRecord.Types.EndHour as EndHour exposing (EndHour(..))
import Pages.TimeRecord.Types.EndMinute as EndMinute exposing (EndMinute(..))
import Pages.TimeRecord.Types.StartHour as StartHour exposing (StartHour(..))
import Pages.TimeRecord.Types.StartMinute as StartMinute exposing (StartMinute(..))
import Types.Message exposing (Message(..))
import Types.Time.ClockTime as ClockTime exposing (ClockTime(..))


type WorkTimeRangeRule
    = WorkingTimeRangeRule StartHour StartMinute EndHour EndMinute


startAndEndMismatch : Message
startAndEndMismatch =
    ErrorMessage "終了時刻には開始時刻よりあとの時刻を入力してください"


validate : WorkTimeRangeRule -> Message
validate workTimeRange =
    case workTimeRange of
        WorkingTimeRangeRule startHour startMinute endHour endMinute ->
            if
                not (StartHour.isValid startHour)
                    || not (StartMinute.isValid startMinute)
                    || not (EndHour.isValid endHour)
                    || not (EndMinute.isValid endMinute)
            then
                EmptyMessage

            else
                let
                    startClock =
                        ClockTime
                            (StartHour.toInt startHour)
                            (StartMinute.toInt startMinute)

                    endClock =
                        ClockTime
                            (EndHour.toInt endHour)
                            (EndMinute.toInt endMinute)
                in
                if ClockTime.isLeftAfter startClock endClock then
                    startAndEndMismatch

                else
                    EmptyMessage
