package com.yoinerdev.quizzesia.data.mappers

import com.yoinerdev.quizzesia.data.dto.Category
import com.yoinerdev.quizzesia.data.dto.Language
import com.yoinerdev.quizzesia.domain.model.CategoryMN
import com.yoinerdev.quizzesia.domain.model.LanguagesMN

fun Language.toLanguagesMN(): LanguagesMN {
    return LanguagesMN(
        id = this.language_id,
        name = this.name + " " + "(${this.nativeName})"
    )
}

fun LanguagesMN.toLanguage(): Language {
    return Language(
        language_id = id,
        name = name.split(" ")[0],
        nativeName = name.split(" ")[1]
    )
}