module Components.AppFooter exposing (appFooter)

import Html exposing (..)
import Html.Attributes exposing (..)


appFooter : Html msg
appFooter =
    footer [ class "footer" ]
        [ div [ class "content has-text-centered" ]
            [ p []
                [ text "from "
                , a [ href "https://github.com/system-sekkei/isolating-the-domain" ] [ text "isolating-the-domain" ]
                , text " by "
                , a [ href "https://elm-lang.org/" ] [ text "elm" ]
                ]
            ]
        ]
