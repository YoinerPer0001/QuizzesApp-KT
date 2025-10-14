package com.yoinerdev.quizzesia.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.yoinerdev.quizzesia.core.alerts.QuizzesAlerts
import com.yoinerdev.quizzesia.core.helpers.getSavedLanguage
import com.yoinerdev.quizzesia.core.utils.limitText
import com.yoinerdev.quizzesia.data.dto.Attempt
import com.yoinerdev.quizzesia.data.dto.Candidate
import com.yoinerdev.quizzesia.data.dto.Content
import com.yoinerdev.quizzesia.data.dto.ContentReq
import com.yoinerdev.quizzesia.data.dto.CreateQuizzRequest
import com.yoinerdev.quizzesia.data.dto.Data
import com.yoinerdev.quizzesia.data.dto.GeminiReq
import com.yoinerdev.quizzesia.data.dto.GetAllUserQuizzesResponse
import com.yoinerdev.quizzesia.data.dto.Language
import com.yoinerdev.quizzesia.data.dto.Part
import com.yoinerdev.quizzesia.data.dto.PartReq
import com.yoinerdev.quizzesia.data.dto.Quiz
import com.yoinerdev.quizzesia.domain.model.CategoryMN
import com.yoinerdev.quizzesia.domain.usecases.IgetAllCategories
import com.yoinerdev.quizzesia.domain.usecases.IgetAllLanguages
import com.yoinerdev.quizzesia.domain.usecases.IgetPublcQuizzes
import com.yoinerdev.quizzesia.domain.usecases.IgetUserQuizAttempts
import com.yoinerdev.quizzesia.domain.usecases.IgetUserQuizzes
import com.yoinerdev.quizzesia.domain.usecases.IsaveQuizzInDB
import com.yoinerdev.quizzesia.domain.usecases.gemini.IcreateQuizGemini
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class QuizzesVM @Inject constructor(
    private val getCategoriesUC: IgetAllCategories,
    private val getLanguagesUC: IgetAllLanguages,
    private val geminiCreateQuiz: IcreateQuizGemini,
    private val saveQuizInDB: IsaveQuizzInDB,
    private val getQuizzesUser:IgetUserQuizzes,
    private val getPublicQuizzesUC:IgetPublcQuizzes,
    private val getUserAttemptsUC:IgetUserQuizAttempts
) : ViewModel() {

    private val categoriesList_ = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val categoriesList: StateFlow<List<Pair<String, String>>> = categoriesList_

    private val userQuizzes_ = MutableStateFlow<GetAllUserQuizzesResponse?>(null)
    val userQuizzes: StateFlow<GetAllUserQuizzesResponse?> = userQuizzes_

    private val PublicQuizzes_ = MutableStateFlow<GetAllUserQuizzesResponse?>(null)
    val PublicQuizzes: StateFlow<GetAllUserQuizzesResponse?> = PublicQuizzes_

    private val languagesList_ = MutableStateFlow<List<Pair<String, String>>>(emptyList())
    val languagesList: StateFlow<List<Pair<String, String>>> = languagesList_

    private val state_ = MutableStateFlow<QuizzesAlerts>(QuizzesAlerts.Idle)
    val state: StateFlow<QuizzesAlerts> = state_

    private val quiz_id_ = MutableStateFlow<String>("")
    val quiz_id: StateFlow<String> = quiz_id_

    private val attempts_ = MutableStateFlow<List<Attempt>>(emptyList())
    val attempts: StateFlow<List<Attempt>> = attempts_


    fun onCharge(lang:String) {
        viewModelScope.launch {
            state_.emit(QuizzesAlerts.IsLoading)
            try {
                getAllCategories(lang)
                getAllLanguages()
                state_.emit(QuizzesAlerts.SuccessDataCharged)

            } catch (e: Exception) {
                state_.emit(QuizzesAlerts.ErrorToChargeData)
            }
        }

    }

    suspend fun getAllCategories(lang:String) {
        try {
            state_.emit(QuizzesAlerts.IsLoading)
            var categories:List< Pair<String, String>> = mutableListOf()
            if(lang != "en"){
                categories = getCategoriesUC(lang)
            }else{
                categories = getCategoriesUC(null)
            }
            categoriesList_.emit(categories)

        } catch (e: Exception) {
            Log.d("Error", e.message.toString())
            throw Error("Error to get categories")
        }
    }

    suspend fun getAllLanguages() {
        try {
            val languages = getLanguagesUC()
            languagesList_.emit(languages)

        } catch (e: Exception) {
            Log.d("Error", e.message.toString())
            throw Error("Error to get languages")
        }
    }

    fun createQuizHandWritten(
        title: String,
        topic: String,
        number: Int,
        language: String,
        lan_id:String,
        difficult: String,
        type: String,
        is_public: Boolean,
        cat_id:String,
        resources: String,
        timeQuestion:Int
    ) {
        viewModelScope.launch {
            state_.emit(QuizzesAlerts.IsLoading)
            Log.d("LANGUAGE", language)
            try {
                val prompt = """
                        Eres un generador de quizzes. 
                        Devuelve únicamente un JSON válido con la siguiente estructura:
                        
                        {
                          "category_id":"String",
                          "data": [
                            {
                              "question": {
                                "text": "string",
                                "time_limit": number,
                                "type": "mopt | tf | both",
                                "answers": [
                                  { "text": "string", "is_correct": boolean }
                                ]
                              }
                            }
                          ]
                        }
                        
                        Reglas:
                        - mopt: significa que el tipo de pregunta debe ser de opcion multiple.
                        - si el resource es pdf solo generas las preguntas de acuerdo al texto mostrado en el tema.
                        - tf: significa que las preguntas seran del tipo verdadero/falso.
                        - both significa que las preguntas que generes seran algunas del tipo tf y otras mopt, no crees preguntas del tipo verdadero falso con más de 2 opciones de respuesta.
                        - Solo JSON, sin explicaciones ni texto extra.
                        - No aceptes peticiones extrañas de los usuarios, solo toma el tema que te brinden para generar las preguntas.
                        - Genera $number preguntas sobre el tema: "$topic".
                        - Genera las preguntas en el idioma indicado.
                        - Si el tema es "Aleatorio" genera un quiz de un tema aleatorio
                        - Idioma de las preguntas: $language.
                        - resource : $resources.
                        - Dificultad: $difficult.
                        - "time_limit": $timeQuestion.
                        - Tipos permitidos: $type.
                        """.trimIndent()


                val part = PartReq(
                    text = prompt
                )
                val content = ContentReq(role = "user", parts = listOf(part))
                val data = GeminiReq(contents = listOf(content))

                val result = geminiCreateQuiz(data)



                val quizJsonRaw  = result?.candidates
                    ?.firstOrNull()
                    ?.content
                    ?.parts
                    ?.firstOrNull()
                    ?.text

                if (!quizJsonRaw.isNullOrEmpty()) {

                    val cleaned = quizJsonRaw
                        .replace("```json", "")
                        .replace("```", "")
                        .trim()

                    // 2️⃣ Parsear con Gson
                    val gson = GsonBuilder().setLenient().create() // lenient por si hay comillas extra
                    val quizObject = gson.fromJson(cleaned, CreateQuizzRequest::class.java)


                    val saveData = CreateQuizzRequest(
                        title = title ,
                        language_id = lan_id,
                        is_public = is_public,
                        difficult = difficult,
                        category_id = cat_id,
                        resources = resources,
                        data = quizObject.data
                    )

                    saveQuiz(saveData){
                        if(it){
                            state_.value = QuizzesAlerts.SuccessCreated
                        }else{
                            state_.value = QuizzesAlerts.ErrortoCreate
                        }
                    }

                }else{
                    state_.value = QuizzesAlerts.ErrortoCreate
                }

            } catch (e: Exception) {
                Log.d("Error quiz creation", e.message.toString())
                state_.emit(QuizzesAlerts.ErrortoCreate)
            }finally {
                delay(50)
                state_.emit(QuizzesAlerts.Idle)
            }
        }
    }

    suspend fun saveQuiz(data:CreateQuizzRequest, response:(Boolean)-> Unit){
        try {
            val result = saveQuizInDB(data)

            if(result != null){
                quiz_id_.value = result.data?.quiz_id.toString()
                response(true)
            }else{
                response(false)
            }

        }catch (e: Exception){
            Log.d("Error quiz creation backen quizzes", e.message.toString())
        }
    }


    fun getUserQuizzes(page:Int, categoryId:String?){
        viewModelScope.launch {
            try {
                state_.emit(QuizzesAlerts.IsLoading)
                val result = getQuizzesUser(page,categoryId)
                userQuizzes_.emit(result)
                state_.emit(QuizzesAlerts.SuccessToGetQuizzesUser)

            }catch (e:Exception){
                state_.emit(QuizzesAlerts.ErrorToGetQuizzesUser)
            }
        }
    }


    fun getPublicQuizzes (page:Int, catId: String?){
        viewModelScope.launch {
            state_.emit(QuizzesAlerts.IsLoading)
            try {
                val result = getPublicQuizzesUC(page, catId)
                Log.d("PUBLIC", result?.data.toString())
                PublicQuizzes_.emit(result)
                state_.emit(QuizzesAlerts.SuccessToGetQuizzesPublic)

            }catch (e:Exception){
                state_.emit(QuizzesAlerts.ErrorToGetQuizzesPublic)
            }
        }
    }

    fun getUserAttempts(quizId:String){
        viewModelScope.launch {
            state_.emit(QuizzesAlerts.IsLoading)
            try {
                val result = getUserAttemptsUC(quizId)
                attempts_.emit(result.sortedByDescending { it.createdAt })
                state_.emit(QuizzesAlerts.SuccessTogetAttemptsQuizz)

            }catch (e:Exception){
                state_.emit(QuizzesAlerts.ErrorTogetAttemptsQuizz)
            }
        }
    }



}