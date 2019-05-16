module Pages.Attendance exposing (Model, routerLink, view)

import Browser
import Html exposing (..)
import Html.Attributes exposing (..)



-- MODEL


type alias Model =
    {}



-- VIEW


view : Model -> Browser.Document msg
view model =
    { title = "勤務時間の一覧"
    , body =
        [ text "勤務時間の一覧"
        , ul []
            [ routerLink "/dashboard"
            , routerLink "/payroll/2019-05"
            , routerLink "/attendance/1/2019-05"
            , routerLink "/timerecord/1/2019-05-01"
            ]
        ]
    }


routerLink : String -> Html msg
routerLink path =
    li [] [ a [ href path ] [ text path ] ]
