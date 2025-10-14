package com.yoinerdev.quizzesia.core.utils

import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import java.io.File

fun extractTextFromPdf(file: File): String {
    val document = PDDocument.load(file)
    val pdfStripper = PDFTextStripper()
    val text = pdfStripper.getText(document)
    document.close()
    return text
}