<%--
    Licensed to the Apache Software Foundation (ASF) under one
    or more contributor license agreements.  See the NOTICE file
    distributed with this work for additional information
    regarding copyright ownership.  The ASF licenses this file
    to you under the Apache License, Version 2.0 (the
    "License"); you may not use this file except in compliance
    with the License.  You may obtain a copy of the License at
    
    http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.
--%><%@page session="false" %><%
%><%@page import="org.apache.sling.api.resource.Resource,
                org.apache.sling.api.resource.ResourceUtil,
                org.apache.sling.api.resource.ValueMap,
                org.apache.sling.sample.slingshot.SlingshotConstants,
                org.apache.sling.api.request.ResponseUtil" %><%
%><%@taglib prefix="sling" uri="http://sling.apache.org/taglibs/sling/1.0" %><%
%><sling:defineObjects/><%
%><%
    final ValueMap attr = resource.getValueMap();
    final String itemTitle = attr.get(SlingshotConstants.PROPERTY_TITLE, resource.getName());
    
    int count = 0;
    for(final Resource current : resource.getChildren()) {
        count++;
    }
%><div class="tile double ui-slingshot-clickable" data-link="<%= request.getContextPath() %><%=resource.getPath()%>.html">
    <div class="tile-content icon">
        <i class="icon-pictures fg-blue"></i>
    </div>
    <div class="brand">
        <span class="label fg-black"><%= ResponseUtil.escapeXml(itemTitle) %></span>
        <span class="badge bg-orange"><%= count %></span>
    </div>
</div>
