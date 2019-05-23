module Pages.TimeRecord exposing (Model, Msg, init, update, view)

import Browser
import Html exposing (..)
import Html.Attributes exposing (..)
import Http
import Json.Decode
import Json.Decode.Pipeline
import Pages.TimeRecord.Types.DaytimeBreakMinute as DaytimeBreakMinute exposing (DaytimeBreakMinute)
import Pages.TimeRecord.Types.EndHour as EndHour exposing (EndHour)
import Pages.TimeRecord.Types.EndMinute as EndMinute exposing (EndMinute)
import Pages.TimeRecord.Types.MidnightBreakMinute as MidnightBreakMinute exposing (MidnightBreakMinute)
import Pages.TimeRecord.Types.StartHour as StartHour exposing (StartHour)
import Pages.TimeRecord.Types.StartMinute as StartMinute exposing (StartMinute)
import Types.Employee.EmployeeName as EmployeeName exposing (EmployeeName)
import Types.Employee.EmployeeNumber as EmployeeNumber exposing (EmployeeNumber)
import Types.Time.WorkDate as WorkDate exposing (WorkDate)
import URLs



-- MODEL


type alias Model =
    { employeeNumber : EmployeeNumber
    , workDate : WorkDate
    , state : PageState
    }


type PageState
    = Initializing
    | Prepared PreparedTimeRecordForm


init : EmployeeNumber -> WorkDate -> ( Model, Cmd Msg )
init employeeNumber workDate =
    ( Model employeeNumber workDate Initializing, getPreparedTimeRecordForm employeeNumber workDate )



-- UPDATE


type Msg
    = PrepareForm (Result Http.Error PreparedTimeRecordForm)


update : Msg -> Model -> ( Model, Cmd Msg )
update msg model =
    case msg of
        PrepareForm result ->
            case result of
                Ok preparedTimeRecordForm ->
                    ( { model | state = Prepared preparedTimeRecordForm }, Cmd.none )

                Err error ->
                    Debug.todo (Debug.toString error)



-- VIEW


view : Model -> Browser.Document msg
view model =
    { title = "勤務時間の入力"
    , body =
        [ h1 [] [ text "勤務時間の入力" ]

        --        TODO impl
        ]
    }



-- HTTP


type alias PreparedTimeRecordForm =
    { contractingEmployees : List Employee
    , preparedRequest : TimeRecordForm
    }


type alias Employee =
    { employeeNumber : EmployeeNumber
    , employeeName : EmployeeName
    }


type alias TimeRecordForm =
    { employeeNumber : EmployeeNumber
    , workDate : WorkDate
    , startHour : StartHour
    , startMinute : StartMinute
    , endHour : EndHour
    , endMinute : EndMinute
    , daytimeBreakTime : DaytimeBreakMinute
    , midnightBreakTime : MidnightBreakMinute
    }


preparedTimeRecordFormDecoder : Json.Decode.Decoder PreparedTimeRecordForm
preparedTimeRecordFormDecoder =
    Json.Decode.succeed PreparedTimeRecordForm
        |> Json.Decode.Pipeline.required "contractingEmployees" (Json.Decode.list employeeDecoder)
        |> Json.Decode.Pipeline.required "preparedRequest" timeRecordFormDecoder


employeeDecoder : Json.Decode.Decoder Employee
employeeDecoder =
    Json.Decode.succeed Employee
        |> Json.Decode.Pipeline.required "employeeNumber" EmployeeNumber.decoder
        |> Json.Decode.Pipeline.required "employeeName" EmployeeName.decoder


timeRecordFormDecoder : Json.Decode.Decoder TimeRecordForm
timeRecordFormDecoder =
    Json.Decode.succeed TimeRecordForm
        |> Json.Decode.Pipeline.required "employeeNumber" EmployeeNumber.decoder
        |> Json.Decode.Pipeline.required "workDate" WorkDate.decoder
        |> Json.Decode.Pipeline.required "startHour" StartHour.decoder
        |> Json.Decode.Pipeline.required "startMinute" StartMinute.decoder
        |> Json.Decode.Pipeline.required "endHour" EndHour.decoder
        |> Json.Decode.Pipeline.required "endMinute" EndMinute.decoder
        |> Json.Decode.Pipeline.required "daytimeBreakTime" DaytimeBreakMinute.decoder
        |> Json.Decode.Pipeline.required "midnightBreakTime" MidnightBreakMinute.decoder


getPreparedTimeRecordForm : EmployeeNumber -> WorkDate -> Cmd Msg
getPreparedTimeRecordForm employeeNumber workDate =
    Http.get
        { url = URLs.timerecordPreparedFormGetEndpoint employeeNumber workDate
        , expect = Http.expectJson PrepareForm preparedTimeRecordFormDecoder
        }
