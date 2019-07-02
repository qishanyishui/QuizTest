package fr.epita.quiz.services.util;

public enum ConfigEntry {

    DB_URL("db.url"),
    DB_USERNAME("db.username"),
    DB_PASSWORD("db.password"),

    DB_USER_INSERT("db.student.insert"),
    DB_USER_UPDATE("db.student.update"),
    DB_USER_DELETE("db.student.delete"),
    DB_USER_QUERY("db.student.query"),
    DB_USER_QUERY_BYNAME("db.student.query.byName"),
    DB_USER_QUERY_BYNAMEANDPASSWORD("db.student.query.byNameAndPassword"),

    DB_QUESTION_INSERT("db.question.insert"),
    DB_QUESTION_QUERYID("db.question.queryId"),
    DB_MCQCHOICE_INSERT("db.mcqchoice.insert"),
    DB_ANSWER_INSERT("db.answer.insert"),
    DB_QUESTION_QUERY("db.question.query"),
    DB_MCQCHOICE_QUERY_BYQID("db.mcqchoice.query.byQid"),
    DB_ANSWER_QUERY_BYAID("db.answer.query.byAid"),
    DB_QUESTION_QUERY_BYNAME("db.question.query.byName"),

    DB_QUESTION_QUERY_BYCONTENT("db.question.query.byContent"),
    DB_QUESTION_DELETE_BYQID("db.question.delete.byQid"),
    DB_MCQCHOICE_DELETE_BYQID("db.mcqchoice.delete.byQid"),
    DB_ANSWER_DELETE_BYQID("db.answer.delete.byQid"),

    DB_OPENQUESTION_INSERT("db.openquestion.insert"),
    DB_QUESTION_QUERY2("db.question.query2"),
    DB_QUESTION_QUERY3("db.question.query3"),
    DB_OPENQUESTION_QUERY("db.openquestion.query"),
    DB_OPENQUESTION_QUERY_BYQID("db.openquestion.query.byQid"),

    DB_QUESTION_QUERY_BYCONTENT2("db.question.query.byContent2"),
    DB_OPENQUESTION_DELETE("db.openquestion.delete"),

    DB_EXAMQUESTION_QUERY_TOPICS("db.examquestion.query.topics"),
    /*DB_EXAMQUESTION_QUERY_MCQQUESTION("db.examquestion.query.mcqquestion"),
    DB_EXAMQUESTION_QUERY_MCQQUESTION_MCQCHOICE("db.examquestion.query.mcqquestion.mcqchoice"),
    DB_EXAMQUESTION_QUERY_OPENQUESTION("db.examquestion.query.openquestion"),*/

    DB_QUIZ_SEARCHQUERY_BYIDORNAME("db.quiz.searchQuery.byIdOrName"),
    DB_QUIZ_DELETE("db.quiz.delete"),
    DB_QUIZ_UPDATE("db.quiz.update"),
    DB_QUIZ_INSERT("db.quiz.insert"),
    DB_QUIZ_CREATE("db.quiz.create"),
    ;
    private String key;

    public String getKey() {
        return key;
    }

    private ConfigEntry(String key) {
        this.key = key;
    }

}
