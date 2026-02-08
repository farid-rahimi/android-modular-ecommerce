package com.solutionium.core.designsystem.composable

import android.graphics.Typeface
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextDecoration
import androidx.core.text.HtmlCompat

/**
 * A Composable that displays HTML text, parsing common tags,
 * but disables link clicking. Links will be styled as plain text
 * or optionally with a specific link-like style without interaction.
 *
 * @param htmlString The HTML string to display.
 * @param modifier Modifier for this text.
 * @param style The base text style to apply.
 * @param linkStyle Optional style to apply to text that was originally a link.
 *                  If null, links will inherit the surrounding text style.
 */
@Composable
fun HtmlText(
    htmlString: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
    linkStyle: SpanStyle? = null // e.g., SpanStyle(color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)
) {
    val annotatedString = rememberHtmlAnnotatedString(htmlString, linkStyle)
    Text(
        text = annotatedString,
        style = style,
        modifier = modifier
    )
}

/**
 * Remembers and converts an HTML string to an AnnotatedString,
 * removing clickability from URLSpans.
 */
@Composable
fun rememberHtmlAnnotatedString(htmlString: String, linkStyle: SpanStyle? = null): AnnotatedString {
    val spanned: Spanned = HtmlCompat.fromHtml(
        htmlString,
        HtmlCompat.FROM_HTML_MODE_LEGACY
    )

    return buildAnnotatedString {
        append(spanned.toString()) // Append the plain text first

        // Re-apply spans from Spanned to AnnotatedString
        spanned.getSpans(0, spanned.length, Any::class.java).forEach { span ->
            val start = spanned.getSpanStart(span)
            val end = spanned.getSpanEnd(span)

            when (span) {
                is StyleSpan -> { // Bold, Italic
                    when (span.style) {
                        Typeface.BOLD -> addStyle(
                            SpanStyle(fontWeight = FontWeight.Bold),
                            start,
                            end
                        )

                        Typeface.ITALIC -> addStyle(
                            SpanStyle(fontStyle = FontStyle.Italic),
                            start,
                            end
                        )

                        Typeface.BOLD_ITALIC -> addStyle(
                            SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic
                            ), start, end
                        )
                    }
                }

                is UnderlineSpan -> {
                    addStyle(SpanStyle(textDecoration = TextDecoration.Underline), start, end)
                }

                is ForegroundColorSpan -> {
                    addStyle(
                        SpanStyle(color = Color(span.foregroundColor)),
                        start,
                        end
                    )
                }
                // Add more span type conversions as needed (e.g., RelativeSizeSpan, BackgroundColorSpan)
                is URLSpan -> {
                    // This is where links are handled.
                    // We apply the linkStyle if provided, but DO NOT add a ClickableTextAnnotation
                    // or use ClickableText, thus disabling the click.
                    linkStyle?.let {
                        addStyle(it, start, end)
                    }
                    // If linkStyle is null, the text within the <a> tag
                    // will simply inherit the style of the surrounding text.
                }
            }
        }
    }
}