package fr.epita.quiz.datamodel;

import java.util.Arrays;

public class Question {

    private String content; //问题内容
    private String[] topics; //问题主题
    private Integer difficulty; //复杂度

    public Question(){

    }

    public Question(String content, String[] topics, Integer difficulty) {
        this.content = content;
        this.topics = topics;
        this.difficulty = difficulty;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }


    @Override
    public String toString() {
        return "Question [content=" + content + ", topics=" + Arrays.toString(topics) + ", difficulty=" + difficulty
                + "]";
    }


}
