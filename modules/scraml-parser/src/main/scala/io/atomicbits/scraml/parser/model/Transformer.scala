/*
 * (C) Copyright 2015 Atomic BITS (http://atomicbits.io).
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Affero General Public License
 * (AGPL) version 3.0 which accompanies this distribution, and is available in
 * the LICENSE file or at http://www.gnu.org/licenses/agpl-3.0.en.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Affero General Public License for more details.
 *
 * Contributors:
 *     Peter Rigole
 *
 */

package io.atomicbits.scraml.parser.model

import scala.collection.JavaConverters._

/**
 * Created by peter on 17/05/15, Atomic BITS bvba (http://atomicbits.io). 
 */
object Transformer {

  def transformMap[O, N](transform: O => N)(oldMap: java.util.Map[String, O]): Map[String, N] = {
    if (oldMap == null) Map.empty
    else {
      oldMap.asScala.foldLeft[Map[String, N]](Map.empty) { (aggr, el) =>
        val (key, value) = el
        aggr + (key -> transform(value))
      }
    }
  }

}