package com.rayrobdod

import sbt._
import java.nio.file.Files
import com.rayrobdod.json.builder.{Builder, SeqBuilder, CaseClassBuilder}

package object tpporg {
	
	val PokemonBuilder = new CaseClassBuilder(
		classOf[Pokemon],
		Pokemon(),
		Map(
			"attacks" -> new SeqBuilder(),
			"nickname" -> new SeqBuilder(),
			"nextAttacks" -> new SeqBuilder()
		)
	)
	
	val BadgeBuilder = new CaseClassBuilder(
		classOf[Badge],
		Badge(),
		Map()
	)
	
}

package tpporg {
	
	case class PageData(
		monsterType:String = "Pokémon",
		fileName:String = "sdf.html",
		lastUpdate:String = "??d ??h ??m",
		party:Seq[Pokemon] = Nil,
		badges:Seq[Badge] = Nil
	)
	
	case class Badge(
		name:String = "???",
		time:String = "??d ??h ??m",
		attempts:Long = 0
	)
	
	case class Pokemon(
		species:String = "???",
		type1:String = "???",
		type2:String = "",
		ability:String = "???",
		ingame:String = "???",
		level:Long = -1,
		attacks:Seq[String] = Seq("???","???","???","???"),
		holding:String = "???",
		nature:String = "???",
		nickname:Seq[String] = Nil,
		nextAttack:String = "???",
		nextAttacks:Seq[String] = Nil,
		caught:String = "???"
	)
	
	
	
	
	
	
	
	class PageDataBuilder() extends Builder[PageData] {
		override val init = PageData()
		override def apply(t:PageData, key:String, value:Any) = key match {
			case "monsterType" => t.copy(monsterType = value.toString)
			case "lastUpdate" => t.copy(lastUpdate = value.toString)
			case "party" => t.copy(party = value.asInstanceOf[Seq[Pokemon]])
			case "badges" => t.copy(badges = value.asInstanceOf[Seq[Badge]])
			case _ => throw new IllegalArgumentException("PageDataBuilder apply key: " + key)
		}
		override def childBuilder(key:String) = key match { 
			case "party" => new SeqBuilder(PokemonBuilder)
			case "badges" => new SeqBuilder(BadgeBuilder)
			case _ => throw new IllegalArgumentException("PageDataBuilder childBuilder key: " + key)
		}
		override def resultType = classOf[PageData]
	}
	
}
