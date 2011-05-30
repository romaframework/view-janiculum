<#if part != "raw" && part!="label">
	<div class="${janiculum.cssClass(null)}" style="${janiculum.inlineStyle(null)}" id="${janiculum.id(null)}">
		<br/>
		<script	src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAUohgYsjq9_rO4h8hi8q0kBQlZIqBcGByMbkK8dnv90n3dBICPhSmJYS5EMOHvBZcSYkNKCE66M9IbQ" type="text/javascript"></script>
		<script type="text/javascript">	//<![CDATA[
		       	 function load() {   
		           	 mapFunction("${janiculum.id("content")}","${janiculum.content()}",15);
		         }
		         function mapFunction(divName,location,zoom) {
		            if (GBrowserIsCompatible()) {      
		              geocoder = new GClientGeocoder();
		              geocoder.getLocations(location, function setCenter(response) {
		                  map = new GMap2(document.getElementById(divName));
		                  map.clearOverlays();
		                  if (!response || response.Status.code != 200) {
		                      alert("Status Code:" + response.Status.code);
		                  } else {
		                      place = response.Placemark[0];
		                      point = new GLatLng(place.Point.coordinates[1],place.Point.coordinates[0]);
		                      map.setCenter(point,zoom)
		                      try {
		                         eval(divName+"map_call_back(map)");
		                      } catch(er) {
				      //do nothing
		   		      }
		   		  }  
		   		     });}}
			     load();
			     //]]>
		</script>
		<div id="${janiculum.id("content")}" class="google_map_generated_map"></div>
		<br/>
	</div>
</#if>

<#-- Part LABEL -->
<#if part = "label">
	<label id="${janiculum.id("label")}" class="${janiculum.cssClass("label")}" for="${janiculum.id("content")}">${janiculum.i18NLabel()}</label>
</#if>
