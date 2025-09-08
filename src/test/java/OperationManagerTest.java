import org.example.model.Line;
import org.example.model.LineCollector;
import org.example.service.OperationManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OperationManagerTest {

    @Mock
    private LineCollector lineCollectorMock;

    private OperationManagerImpl operationManager;

    @BeforeEach
    void setUp() {
        operationManager = new OperationManagerImpl(lineCollectorMock);
    }

    @ParameterizedTest
    @MethodSource("getDataForFindLinesForInterval")
    void findLinesForInterval(List<Line> input, List<Line> expected) {
        LineCollector lineCollector = mock(LineCollector.class);
        when(lineCollector.getLines()).thenReturn(input);

        OperationManagerImpl operationManager = new OperationManagerImpl(lineCollector);
        operationManager.findLinesForInterval();

        assertEquals(expected, operationManager.getResultGroupLine());
    }

    @ParameterizedTest
    @MethodSource("getDataForVerifySuccess")
    void verifyInputSuccess(String input, Line expected) {
        assertEquals(expected, operationManager.verifyInput(input));
    }

    @ParameterizedTest
    @MethodSource("getDataForVerifyFailedThrows")
    void verifyInputFailedThrows(String input) {
        assertThrows(IllegalArgumentException.class, () -> operationManager.verifyInput(input));
    }

    static Stream<Arguments> getDataForFindLinesForInterval() {
        return Stream.of(
                Arguments.of(
                        List.of(new Line(1,10), new Line(1,3), new Line(1,2), new Line(6,8), new Line(8,10), new Line(9,20)),
                        List.of(new Line(1,10), new Line(9,20))
                ),
                Arguments.of(
                        List.of(new Line(1,4), new Line(2,8), new Line(5,6), new Line(10,13)),
                        List.of(new Line(1,4), new Line(2,8), new Line(10,13))
                ),
                Arguments.of(
                        List.of(new Line(-2,1), new Line(-1,2), new Line(3,11), new Line(3,8)),
                        List.of(new Line(-2,1), new Line(-1,2), new Line(3,11))
                ),
                // new - overlapping
                Arguments.of(
                        List.of(new Line(1,5), new Line(2,6), new Line(4,7), new Line(8,10)),
                        List.of(new Line(1,5), new Line(4,7), new Line(8,10))
                ),
                // new - overlapping
                Arguments.of(
                        List.of(new Line(10,15), new Line(12,17), new Line(15,20), new Line(30,33), new Line(32,36)),
                        List.of(new Line(10,15), new Line(15,20), new Line(30,33), new Line(32,36))
                )
        );
    }

    static Stream<Arguments> getDataForVerifySuccess() {
        return Stream.of(
                Arguments.of(
                        "1 2",
                        new Line(1,2)
                ),
                Arguments.of(
                        "-1 20",
                        new Line(-1,20)
                ),
                Arguments.of(
                        "111 222",
                        new Line(111,222)
                )
        );
    }

    static Stream<Arguments> getDataForVerifyFailedThrows() {
        return Stream.of(
                Arguments.of("12"),
                Arguments.of("20 1"),
                Arguments.of("0"),
                Arguments.of("1"),
                Arguments.of("9 8"),
                Arguments.of("555")
        );
    }
}