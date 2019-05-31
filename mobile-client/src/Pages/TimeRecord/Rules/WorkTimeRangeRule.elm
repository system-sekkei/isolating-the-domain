module Pages.TimeRecord.Rules.WorkTimeRangeRule exposing (WorkTimeRangeRule(..), errorMessage, isValid, validate)

import Pages.TimeRecord.Types.EndHour as EndHour exposing (EndHour(..))
import Pages.TimeRecord.Types.EndMinute as EndMinute exposing (EndMinute(..))
import Pages.TimeRecord.Types.StartHour as StartHour exposing (StartHour(..))
import Pages.TimeRecord.Types.StartMinute as StartMinute exposing (StartMinute(..))
import Types.Message as Message exposing (Message(..))
import Types.Time.ClockTime as ClockTime exposing (ClockTime(..))


type WorkTimeRangeRule
    = ValidWorkTimeRange
    | InvalidWorkTimeRange Message


startAndEndMismatch : Message
startAndEndMismatch =
    ErrorMessage "終了時刻には開始時刻よりあとの時刻を入力してください"


validate : StartHour -> StartMinute -> EndHour -> EndMinute -> WorkTimeRangeRule
validate startHour startMinute endHour endMinute =
    if
        not (StartHour.isValid startHour)
            || not (StartMinute.isValid startMinute)
            || not (EndHour.isValid endHour)
            || not (EndMinute.isValid endMinute)
    then
        ValidWorkTimeRange

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
            InvalidWorkTimeRange startAndEndMismatch

        else
            ValidWorkTimeRange


isValid : WorkTimeRangeRule -> Bool
isValid workTimeRangeRule =
    case workTimeRangeRule of
        ValidWorkTimeRange ->
            True

        _ ->
            False


errorMessage : WorkTimeRangeRule -> String
errorMessage workTimeRangeRule =
    case workTimeRangeRule of
        InvalidWorkTimeRange message ->
            Message.toString message

        _ ->
            ""
