module Pages.TimeRecord.Rules.BreakTimeCapRule exposing (BreakTimeCapRule(..), errorMessage, isValid, validate)

import Pages.TimeRecord.Types.DaytimeBreakMinute as DaytimeBreakMinute exposing (DaytimeBreakMinute)
import Pages.TimeRecord.Types.EndHour as EndHour exposing (EndHour)
import Pages.TimeRecord.Types.EndMinute as EndMinute exposing (EndMinute)
import Pages.TimeRecord.Types.MidnightBreakMinute as MidnightBreakMinute exposing (MidnightBreakMinute)
import Pages.TimeRecord.Types.StartHour as StartHour exposing (StartHour)
import Pages.TimeRecord.Types.StartMinute as StartMinute exposing (StartMinute)
import Types.Message as Message exposing (Message(..))
import Types.Time.ClockTime as ClockTime exposing (ClockTime(..))


type BreakTimeCapRule
    = ValidBreakTimeCapRule
    | InvalidBreakTimeCapRule Message


overCap : Message
overCap =
    ErrorMessage "休憩時間の合計が勤務時間を超えています"


validate : StartHour -> StartMinute -> EndHour -> EndMinute -> DaytimeBreakMinute -> MidnightBreakMinute -> BreakTimeCapRule
validate startHour startMinute endHour endMinute daytimeBreakMinute midnightBreakMinute =
    if
        not (StartHour.isValid startHour)
            || not (StartMinute.isValid startMinute)
            || not (EndHour.isValid endHour)
            || not (EndMinute.isValid endMinute)
            || not (DaytimeBreakMinute.isValid daytimeBreakMinute)
            || not (MidnightBreakMinute.isValid midnightBreakMinute)
    then
        ValidBreakTimeCapRule

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

            totalWorkMinute =
                ClockTime.diffInMinutes startClock endClock

            totalBreakTimeMinute =
                DaytimeBreakMinute.toInt daytimeBreakMinute
                    + MidnightBreakMinute.toInt midnightBreakMinute
        in
        if totalBreakTimeMinute >= totalWorkMinute then
            InvalidBreakTimeCapRule overCap

        else
            ValidBreakTimeCapRule


isValid : BreakTimeCapRule -> Bool
isValid breakTimeCapRule =
    case breakTimeCapRule of
        ValidBreakTimeCapRule ->
            True

        _ ->
            False


errorMessage : BreakTimeCapRule -> String
errorMessage breakTimeCapRule =
    case breakTimeCapRule of
        InvalidBreakTimeCapRule message ->
            Message.toString message

        _ ->
            ""
