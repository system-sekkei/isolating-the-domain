module Pages.Dashboard exposing (view)

import Browser
import Html exposing (..)
import Html.Attributes exposing (..)



-- VIEW


view : Browser.Document msg
view =
    { title = "ダッシュボード"
    , body =
        [ text "ダッシュボード"
        , ul []
            [ routerLink "/dashboard"
            , routerLink "/payroll/2019-05"
            , routerLink "/attendance/1/2019-05"
            , routerLink "/timerecord/1/2019-05-01"
            , routerLink "/notfound"
            ]
        ]
    }


routerLink : String -> Html msg
routerLink path =
    li [] [ a [ href path ] [ text path ] ]
