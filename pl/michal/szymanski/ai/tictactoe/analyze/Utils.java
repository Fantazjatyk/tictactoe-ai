/* 
 * The MIT License
 *
 * Copyright 2017 Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package pl.michal.szymanski.ai.tictactoe.analyze;

import pl.michal.szymanski.ai.tictactoe.analyze.Comparators;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import pl.michal.szymanski.ai.tictactoe.model.Board;
import pl.michal.szymanski.ai.tictactoe.model.BoardField;
import pl.michal.szymanski.ai.tictactoe.model.FieldOwner;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public final class Utils {

    private Utils() {

    }

    public static List<BoardField[]> getFreeRows(Board b) {
        return b.getSelector().getRows().stream().filter(el -> countOwnerFields(el, FieldOwner.Unknown) == el.length).collect(Collectors.toList());
    }

    public static int countOwnerFields(BoardField[] fields, FieldOwner f) {
        int size = (int) Stream.of(fields).filter(el -> el.getOwner() == f).count();
        return size;
    }

    public static List<BoardField[]> getFreeColumns(Board b) {
        return b.getSelector().getColumns().stream().filter(el -> countOwnerFields(el, FieldOwner.Unknown) == el.length).collect(Collectors.toList());
    }

    public static List<BoardField[]> getFreeDiagonals(Board b) {
        return b.getSelector().getDiagonals().stream().filter(el -> countOwnerFields(el, FieldOwner.Unknown) == 3).collect(Collectors.toList());
    }

    public static List<BoardField[]> getFreeWhatever(Board b) {
        List<BoardField[]> result = new ArrayList();
        result.addAll(getFreeRows(b));
        result.addAll(getFreeColumns(b));
        result.addAll(getFreeDiagonals(b));

        return result;
    }

    public static List<BoardField[]> getWhateverSortedByOwner(Board board, FieldOwner owner) {
        List<BoardField[]> result = new ArrayList();
        result.addAll(board.getSelector().getColumns());
        result.addAll(board.getSelector().getRows());
        result.addAll(board.getSelector().getDiagonals());

        result.sort(Comparators.comparatorOfOwnerFieldsCount(owner));
        return result;
    }

    public static List<BoardField[]> getWhateverSortedByCompartor(Board board, Comparator<BoardField[]> f) {
        List<BoardField[]> result = new ArrayList();
        result.addAll(board.getSelector().getColumns());
        result.addAll(board.getSelector().getRows());
        result.addAll(board.getSelector().getDiagonals());

        result.sort((a, b) -> f.compare(a, b));
        return result;
    }

    public static List<BoardField> getFreeLinePoints(BoardField[] line) {
        return Stream.of(line).filter(el -> el.getOwner() == FieldOwner.Unknown).collect(Collectors.toList());
    }

    public static boolean isBoardClear(Board board) {
        return board.getSelector().getAllFields().stream().anyMatch(el -> el.getOwner() != FieldOwner.Unknown);
    }

}
