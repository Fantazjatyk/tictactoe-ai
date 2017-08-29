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

import java.util.stream.Stream;
import pl.michal.szymanski.ai.tictactoe.model.BoardField;
import pl.michal.szymanski.ai.tictactoe.model.FieldOwner;

/**
 *
 * @author Michał Szymański, kontakt: michal.szymanski.aajar@gmail.com
 */
public interface BoardReviewer {

    public static int rateFieldsLine(BoardField[] fields) {
        int myFields = (int) Stream.of(fields).filter(el -> el.getOwner() == FieldOwner.Me).count();
        int unknownFields = (int) Stream.of(fields).filter(el -> el.getOwner() == FieldOwner.Unknown).count();
        int notMyFields = (int) Stream.of(fields).filter(el -> el.getOwner() == FieldOwner.NotMe).count();

        if (notMyFields > 0) {
            return -notMyFields;
        }

        return unknownFields + (myFields * 2);
    }

    public static int rateFieldsLineToFactorInNotMyFields(BoardField[] fields) {
        int notMyFields = (int) Stream.of(fields).filter(el -> el.getOwner() == FieldOwner.NotMe).count();

        if (notMyFields == fields.length - 1) {
            return fields.length * 2 + 2;
        }
        return rateFieldsLine(fields);
    }
}
