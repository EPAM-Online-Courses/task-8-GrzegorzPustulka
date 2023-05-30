package efs.task.unittests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FitCalculatorTest {

    @Test
    void shouldReturnTrue_whenDietRecommended() {
        //given
        double weight = 89.2;
        double height = 1.72;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertTrue(recommended);
    }

    @Test
    void shouldReturnFalse_whenDietRecommended() {
        //given
        double weight = 69.5;
        double height = 1.72;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertFalse(recommended);
    }

    @Test
    void shouldThrowException_whenInvalidUserHeight() {
        //given
        double weight = 69.5;
        double height = 0;

        //then
        assertThrows(IllegalArgumentException.class, () -> FitCalculator.isBMICorrect(weight, height));
    }

    @ParameterizedTest(name = "BMI is correct for weight {0}")
    @ValueSource(doubles = { 81.0,79.5,80.5})
    void shouldReturnTrue_whenValidData(double weight) {
        //given
        double height = 1.72;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight,height);

        //then
        assertTrue(recommended);
    }

    @ParameterizedTest(name = "BMI is not correct for weight {0} and height {1}")
    @CsvSource({
            "81.0,1.95",
            "69.5,1.80",
            "55.5,2.10"
    })
    void shouldReturnFalse_whenInvalidData(double weight,double height) {
        //when
        boolean recommended = FitCalculator.isBMICorrect(weight,height);

        //then
        assertFalse(recommended);
    }

    @ParameterizedTest(name = "BMI is not correct for weight {0} and height {1}")
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void shouldReturnFalse_whenInvalidDataFromFile(double weight, double height){
        //when
        boolean recommended = FitCalculator.isBMICorrect(weight,height);

        //then
        assertFalse(recommended);
    }

    @Test
    void shouldReturnUserWithWorstBMI_whenValidUsersList(){
        //given
        List<User> userList = TestConstants.TEST_USERS_LIST;
        User expectedUser = new User(1.79,97.3);

        //when
        User actualUser = FitCalculator.findUserWithTheWorstBMI(userList);

        //then
        assertEquals(expectedUser.getWeight(), actualUser.getWeight());
        assertEquals(expectedUser.getHeight(), actualUser.getHeight());
    }

    @Test
    void shouldReturnNull_whenEmptyUsersList() {
        //given
        List<User> emptyList = Collections.emptyList();

        //when
        User actualUser = FitCalculator.findUserWithTheWorstBMI(emptyList);

        //then
        assertNull(actualUser);
    }

    @Test
    void shouldReturnCorrectBMIScoreList_whenValidUsersList() {
        //given
        List<User> userList = TestConstants.TEST_USERS_LIST;
        double[] expectedBMIScoreList = TestConstants.TEST_USERS_BMI_SCORE;

        //when
        double[] actualBMIScoreList = FitCalculator.calculateBMIScore(userList);

        //then
        assertArrayEquals(expectedBMIScoreList, actualBMIScoreList, 0.01);
    }
}