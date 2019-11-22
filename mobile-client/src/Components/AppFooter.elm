module Components.AppFooter exposing (appFooter)

import Components.AppHtmlUtils exposing (nextLine)
import Html exposing (..)
import Html.Attributes exposing (..)


appFooter : Html msg
appFooter =
    footer [ class "footer" ]
        [ div [ class "content has-text-centered" ]
            [ p []
                [ text "from "
                , a [ href "https://github.com/system-sekkei/isolating-the-domain" ] [ text "isolating-the-domain" ]
                , nextLine
                , text " by "
                , a [ href "https://elm-lang.org/" ] [ text "elm" ]
                , text " and "
                , a [ href "https://bulma.io/" ] [ text "Bluma" ]
                ]
            ]
        ]
