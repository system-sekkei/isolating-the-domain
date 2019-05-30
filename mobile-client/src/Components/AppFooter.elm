module Components.AppFooter exposing (appFooter)

import Html exposing (..)
import Html.Attributes exposing (..)


appFooter : Html msg
appFooter =
    footer [ class "footer" ]
        [ div [ class "content has-text-centered" ]
            [ p []
                [ text "via ", a [ href "https://github.com/system-sekkei/isolating-the-domain" ] [ text "isolating-the-domain" ] ]
            ]
        ]
