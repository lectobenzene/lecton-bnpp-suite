package experiments

import groovy.xml.MarkupBuilder

class DynamoAccess {


	static main(args) {

		DynamoAccess.getMetaClass().'dome' = 100
		def langList = ['AA', 'BB', 'CC']
		langList.each {
			DynamoAccess.getMetaClass()."writer$it" = new StringWriter()
			def temp= new DynamoAccess()
			DynamoAccess.getMetaClass()."builder$it" = new MarkupBuilder(temp."writer$it")
			temp."builder$it".setDoubleQuotes(true)
		}

		DynamoAccess access = new DynamoAccess()
		def fieldName = 'dome'
		println access[fieldName]


		langList.each {
			access["builder$it"].string(name:"_$it", "The Factor is $it. Thats all")
		}
		println access.writerAA.toString()

		def map = ['EN':0,'DE':4]
		println "Key with value 4 is ${getKeyFromValue(map, 0)}"


		/*
		 * MERGING two list virtually
		 */
		def oneList = [1, 2, 3, 4, 5]
		def twoList = [7, 8, 9]
		[oneList, twoList].flatten().each {
			println it
		}



		/*
		 * Adding to map dynamically . Just to see if SINGLE QUOTES 
		 * is getting reflected as KEY for the map
		 */
		def maps = [:]
		maps['EN'] = "EN is greater"
		maps['FR'] = "FR is greater"
		['EN', 'FR'].each{
			maps["$it"] = "$it is greater"
		}
		println maps.inspect()


		/*
		 * To check for Dynamically perform language integration
		 */
		def row = [0:'XXXXX', 1:'a', 2:'b', 3:'c', 4:'d', 5:'e', 6:'f', 7:'g']
		def columnMap = ['TEXT ID':0, 'EN':3, 'FR':6]
		def idColumn = ['TEXT ID']
		def ommitedList = [555]
		def multiLangID

		def idColumnNo = columnMap.find {
			idColumn.contains(it.key)
		}.value
		println idColumnNo

		for(cell in row){
			def value = cell.value
			def columnIndex = cell.key

			columnMap.each {
				mKey, mValue ->
				if(columnIndex == idColumnNo){
					println "$mKey found - Value=$value"
					if(ommitedList.contains(value)){
						//break
					}
					multiLangID = value
				}else if(mValue == columnIndex){
					println "MultiLangID = $multiLangID ----- Value=$value"
				}
			}
		}


		/*
		 * Optimizing the above code /  DOESNT WORK - This is much more time expensive
		 */
		def cellCounter = 0
		for(cell in row){
			cellCounter++
			def value = cell.value
			def columnIndex = cell.key

			def closureCounter = 0
			try{
				columnMap.each {
					mKey, mValue ->
					closureCounter++
					if(columnIndex == idColumnNo){
						println "$mKey found - Value=$value"
						if(ommitedList.contains(value)){
							//break
						}
						multiLangID = value
						throw new Exception()
					}else if(mValue == columnIndex){
						println "MultiLangID = $multiLangID ----- Value=$value"
						throw new Exception()
					}
				}
			} catch(Exception e) {
			
			}
			println "Closure Counter = $closureCounter"
		}
		println "Cell Counter = $cellCounter"
		
		
		
		/*
		 * Changing each to find
		 */
		println 'Changing each to find'
		cellCounter = 0
		for(cell in row){
			cellCounter++
			def value = cell.value
			def columnIndex = cell.key

			def closureCounter = 0
			columnMap.find {
				mKey, mValue ->
				closureCounter++
				if(columnIndex == idColumnNo){
					println "$mKey found - Value=$value"
					if(ommitedList.contains(value)){
						//break
					}
					multiLangID = value
					return true
				}else if(mValue == columnIndex){
					println "MultiLangID = $multiLangID ----- Value=$value"
					return true
				}
			}
			println "Closure Counter = $closureCounter"
		}
		println "Cell Counter = $cellCounter"
	}

	def static getKeyFromValue(map, value){
		def key
		map.each {
			mKey,mValue ->
			if(mValue == value) key = mKey
		}
		return key
	}
}
