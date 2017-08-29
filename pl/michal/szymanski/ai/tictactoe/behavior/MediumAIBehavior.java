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
package pl.michal.szymanski.ai.tictactoe.behavior;

import java.awt.Point;
import java.util.List;
import java.util.stream.Collectors;
import pl.michal.szymanski.ai.tictactoe.Context;
import pl.michal.szymanski.ai.tictactoe.analyze.Comparators;
import pl.michal.szymanski.ai.tictactoe.analyze.Utils;
import pl.michal.szymanski.ai.tictactoe.model.Board;
import pl.michal.szymanski.ai.tictactoe.model.BoardField;
import pl.michal.szymanski.ai.tictactoe.model.FieldOwner;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public class MediumAIBehavior extends EasyAIBehavior {

    @Override
    public Point generateMove(Context ctx) {
        Point result = null;

        Board board = new Board(ctx.getBoardHistory().pollLast());
        List<BoardField[]> line = Utils.getWhateverSortedByCompartor(board, Comparators.comparatorOfSimpleFieldsRating());
        result = attemptToGenerateOffensiveMove(line);

        if (result == null) {
            result = attemptToGeneratePassiveMove(line);
        }
        if (result == null) {
            result = generateRandomAvailableMove(board);
        }
        return result;
    }

    public Point attemptToGenerateOffensiveMove(List<BoardField[]> sorted) {
        sorted
                = sorted.stream().filter(el -> Utils.countOwnerFields(el, FieldOwner.NotMe) > 0)
                        .sorted(Comparators.comparatorOfOwnerFieldsCount(FieldOwner.NotMe))
                        .collect(Collectors.toList());

        if (!sorted.isEmpty()) {
            List<BoardField> freePoints = Utils.getFreeLinePoints(sorted.get(0));
            if (!freePoints.isEmpty()) {
                BoardField f = freePoints.get(0);
                return new Point(f.getX(), f.getY());
            }
        }
        return null;
    }

}
