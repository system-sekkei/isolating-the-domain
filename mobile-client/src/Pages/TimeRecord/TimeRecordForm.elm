module Pages.TimeRecord.TimeRecordForm exposing (TimeRecordForm, decoder, encode, isInvalid, validate)

import Json.Decode
import Json.Decode.Pipeline
import Json.Encode
import Pages.TimeRecord.Rules.BreakTimeCapRule as BreakTimeCapRule exposing (BreakTimeCapRule(..))
import Pages.TimeRecord.Rules.WorkTimeRangeRule as WorkingTimeRangeRule exposing (WorkTimeRangeRule(..))
import Pages.TimeRecord.Types.DaytimeBreakMinute as DaytimeBreakMinute exposing (DaytimeBreakMinute)
import Pages.TimeRecord.Types.EndHour as EndHour exposing (EndHour)
import Pages.TimeRecord.Types.EndMinute as EndMinute exposing (EndMinute)
import Pages.TimeRecord.Types.MidnightBreakMinute as MidnightBreakMinute exposing (MidnightBreakMinute)
import Pages.TimeRecord.Types.StartHour as StartHour exposing (StartHour)
import Pages.TimeRecord.Types.StartMinute as StartMinute exposing (StartMinute)
import Types.Employee.EmployeeNumber as EmployeeNumber exposing (EmployeeNumber)
import Types.Time.WorkDate as WorkDate exposing (WorkDate)


type alias TimeRecordForm =
    { workTimeRangeRule : WorkTimeRangeRule
    , breakTimeCapRule : BreakTimeCapRule
    , employeeNumber : EmployeeNumber
    , workDate : WorkDate
    , startHour : StartHour
    , startMinute : StartMinute
    , endHour : EndHour
    , endMinute : EndMinute
    , daytimeBreakTime : DaytimeBreakMinute
    , midnightBreakTime : MidnightBreakMinute
    }


decoder : Json.Decode.Decoder TimeRecordForm
decoder =
    Json.Decode.succeed (TimeRecordForm ValidWorkTimeRange ValidBreakTimeCapRule)
        |> Json.Decode.Pipeline.required "employeeNumber" EmployeeNumber.decoder
        |> Json.Decode.Pipeline.required "workDate" WorkDate.decoder
        |> Json.Decode.Pipeline.required "startHour" StartHour.decoder
        |> Json.Decode.Pipeline.required "startMinute" StartMinute.decoder
        |> Json.Decode.Pipeline.required "endHour" EndHour.decoder
        |> Json.Decode.Pipeline.required "endMinute" EndMinute.decoder
        |> Json.Decode.Pipeline.required "daytimeBreakTime" DaytimeBreakMinute.decoder
        |> Json.Decode.Pipeline.required "midnightBreakTime" MidnightBreakMinute.decoder


encode : TimeRecordForm -> Json.Encode.Value
encode form =
    Json.Encode.object
        [ ( "employeeNumber", EmployeeNumber.encode form.employeeNumber )
        , ( "workDate", WorkDate.encode form.workDate )
        , ( "startHour", StartHour.encode form.startHour )
        , ( "startMinute", StartMinute.encode form.startMinute )
        , ( "endHour", EndHour.encode form.endHour )
        , ( "endMinute", EndMinute.encode form.endMinute )
        , ( "daytimeBreakTime", DaytimeBreakMinute.encode form.daytimeBreakTime )
        , ( "midnightBreakTime", MidnightBreakMinute.encode form.midnightBreakTime )
        ]


validate : TimeRecordForm -> TimeRecordForm
validate form =
    let
        singleFieldValidated =
            TimeRecordForm
                ValidWorkTimeRange
                ValidBreakTimeCapRule
                form.employeeNumber
                (WorkDate.validate form.workDate)
                (StartHour.validate form.startHour)
                (StartMinute.validate form.startMinute)
                (EndHour.validate form.endHour)
                (EndMinute.validate form.endMinute)
                (DaytimeBreakMinute.validate form.daytimeBreakTime)
                (MidnightBreakMinute.validate form.midnightBreakTime)

        validatedWorkingTimeRangeRule =
            WorkingTimeRangeRule.validate
                singleFieldValidated.startHour
                singleFieldValidated.startMinute
                singleFieldValidated.endHour
                singleFieldValidated.endMinute

        validatedBreakTimeCapRule =
            BreakTimeCapRule.validate
                singleFieldValidated.startHour
                singleFieldValidated.startMinute
                singleFieldValidated.endHour
                singleFieldValidated.endMinute
                singleFieldValidated.daytimeBreakTime
                singleFieldValidated.midnightBreakTime
    in
    { singleFieldValidated
        | workTimeRangeRule = validatedWorkingTimeRangeRule
        , breakTimeCapRule = validatedBreakTimeCapRule
    }


isInvalid : TimeRecordForm -> Bool
isInvalid form =
    not
        (WorkDate.isValid form.workDate
            && StartHour.isValid form.startHour
            && StartMinute.isValid form.startMinute
            && EndHour.isValid form.endHour
            && EndMinute.isValid form.endMinute
            && DaytimeBreakMinute.isValid form.daytimeBreakTime
            && MidnightBreakMinute.isValid form.midnightBreakTime
            && WorkingTimeRangeRule.isValid form.workTimeRangeRule
            && BreakTimeCapRule.isValid form.breakTimeCapRule
        )
