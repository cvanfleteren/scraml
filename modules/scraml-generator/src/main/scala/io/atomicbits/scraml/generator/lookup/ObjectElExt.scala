/*
 *
 *  (C) Copyright 2015 Atomic BITS (http://atomicbits.io).
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the GNU Affero General Public License
 *  (AGPL) version 3.0 which accompanies this distribution, and is available in
 *  the LICENSE file or at http://www.gnu.org/licenses/agpl-3.0.en.html
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Affero General Public License for more details.
 *
 *  Contributors:
 *      Peter Rigole
 *
 */

package io.atomicbits.scraml.generator.lookup

import io.atomicbits.scraml.jsonschemaparser.{AbsoluteId, Id}
import io.atomicbits.scraml.jsonschemaparser.model.{OneOf, ObjectEl, Selection, Schema}

/**
 * Created by peter on 5/07/15. 
 */
case class ObjectElExt(id: AbsoluteId,
                       properties: Map[String, Schema],
                       required: Boolean,
                       requiredFields: List[String] = List.empty,
                       selection: Option[Selection] = None,
                       fragments: Map[String, Schema] = Map.empty,
                       parent: Option[AbsoluteId] = None,
                       children: List[AbsoluteId] = List.empty,
                       typeVariables: List[String] = List.empty,
                       typeDiscriminator: Option[String] = None,
                       typeDiscriminatorValue: Option[String] = None) {

  def hasChildren: Boolean = children.nonEmpty

  def hasParent: Boolean = parent.isDefined

  def isInTypeHiearchy: Boolean = hasChildren || hasParent

  def topLevelParent(schemaLookup: SchemaLookup): Option[ObjectElExt] = {

    def findTopLevelParent(absoluteId: AbsoluteId): ObjectElExt = {
      val objElExt = schemaLookup.objectMap(absoluteId)
      objElExt.parent match {
        case Some(parentId) => findTopLevelParent(parentId)
        case None           => objElExt
      }
    }

    parent.map(findTopLevelParent)

  }

}


object ObjectElExt {

  def apply(obj: ObjectEl): ObjectElExt =
    ObjectElExt(
      id = SchemaUtil.asAbsoluteId(obj.id),
      properties = obj.properties,
      required = obj.required,
      requiredFields = obj.requiredFields,
      selection = obj.selection,
      fragments = obj.fragments,
      typeVariables = obj.typeVariables,
      typeDiscriminator = obj.typeDiscriminator
    )

}
