/* 
 * Copyright 2014 Igor Maznitsa (http://www.igormaznitsa.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.igormaznitsa.jbbp.model;

import com.igormaznitsa.jbbp.compiler.JBBPNamedFieldInfo;
import java.io.Serializable;
import org.junit.Test;
import static org.junit.Assert.*;

public class JBBPFieldArrayByteTest {
  private final byte [] array = new byte[]{(byte) -1, 0, 1, 2, 3};
  private final JBBPFieldArrayByte test = new JBBPFieldArrayByte(new JBBPNamedFieldInfo("test.field", "field", 999), array);

  @Test
  public void testNameAndOffset() {
    assertEquals("test.field", test.getFieldPath());
    assertEquals("field", test.getFieldName());
    assertNotNull(test.getNameInfo());
    assertEquals(999, test.getNameInfo().getFieldOffsetInCompiledBlock());
  }

  @Test
  public void testSize() {
    assertEquals(5, test.size());
  }

  @Test
  public void testGetArray() {
    assertArrayEquals(new byte[]{(byte) -1, 0, 1, 2, 3}, test.getArray());
  }

  @Test
  public void testGetAsBool() {
    final boolean[] etalon = new boolean[]{true, false, true, true, true};
    for (int i = 0; i < etalon.length; i++) {
      assertEquals(etalon[i], test.getAsBool(i));
    }
  }

  @Test
  public void testGetAsInt() {
    final int[] etalon = new int[]{-1, 0, 1, 2, 3};
    for (int i = 0; i < etalon.length; i++) {
      assertEquals(etalon[i], test.getAsInt(i));
    }
  }

  @Test
  public void testGetAsLong() {
    final long[] etalon = new long[]{-1L, 0L, 1L, 2L, 3L};
    for (int i = 0; i < etalon.length; i++) {
      assertEquals(etalon[i], test.getAsLong(i));
    }
  }

  @Test
  public void testIterable() {
    final int[] etalon = new int[]{-1, 0, 1, 2, 3};
    int index = 0;
    for (final JBBPFieldByte f : test) {
      assertEquals(etalon[index++], f.getAsInt());
    }
  }

  @Test
  public void testGetElementAt() {
    final int[] etalon = new int[]{-1, 0, 1, 2, 3};
    final Serializable payload = new FakePayload();
    test.setPayload(payload);
    for (int i = 0; i < etalon.length; i++) {
      final JBBPFieldByte f = test.getElementAt(i);
      assertSame(payload, f.getPayload());
      assertEquals(etalon[i], f.getAsInt());
    }
  }
  
  @Test
  public void testGetValueArrayAsObject() {
    assertArrayEquals(array, (byte[]) test.getValueArrayAsObject(false));

    final byte[] inverted = (byte[]) test.getValueArrayAsObject(true);
    assertEquals(array.length, inverted.length);
    for (int i = 0; i < array.length; i++) {
      assertEquals(JBBPFieldByte.reverseBits(array[i]), inverted[i]);
    }
  }
  
}
