module Pages.TimeRecord.Rules.BreakTimeCapRule exposing (BreakTimeCapRule(..), validate)

import Pages.TimeRecord.Types.DaytimeBreakMinute as DaytimeBreakMinute exposing (DaytimeBreakMinute)
import Pages.TimeRecord.Types.EndHour as EndHour exposing (EndHour)
import Pages.TimeRecord.Types.EndMinute as EndMinute exposing (EndMinute)
import Pages.TimeRecord.Types.MidnightBreakMinute as MidnightBreakMinute exposing (MidnightBreakMinute)
import Pages.TimeRecord.Types.StartHour as StartHour exposing (StartHour)
import Pages.TimeRecord.Types.StartMinute as StartMinute exposing (StartMinute)
import Types.Message exposing (Message(..))
import Types.Time.ClockTime as ClockTime exposing (ClockTime(..))


type BreakTimeCapRule
    = BreakTimeCapRule StartHour StartMinute EndHour EndMinute DaytimeBreakMinute MidnightBreakMinute


overCap : Message
overCap =
    ErrorMessage "休憩時間の合計が勤務時間を超えています"


validate : BreakTimeCapRule -> Message
validate breakTimeCapRule =
    case breakTimeCapRule of
        BreakTimeCapRule startHour startMinute endHour endMinute daytimeBreakMinute midnightBreakMinute ->
            if
                not (StartHour.isValid startHour)
                    || not (StartMinute.isValid startMinute)
                    || not (EndHour.isValid endHour)
                    || not (EndMinute.isValid endMinute)
                    || not (DaytimeBreakMinute.isValid daytimeBreakMinute)
                    || not (MidnightBreakMinute.isValid midnightBreakMinute)
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

                    totalWorkMinute =
                        ClockTime.diffInMinutes startClock endClock

                    totalBreakTimeMinute =
                        DaytimeBreakMinute.toInt daytimeBreakMinute
                            + MidnightBreakMinute.toInt midnightBreakMinute
                in
                if totalBreakTimeMinute >= totalWorkMinute then
                    overCap

                else
                    EmptyMessage
