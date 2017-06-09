<%-- 
    Document   : test
    Created on : Sep 2, 2015, 1:02:32 AM
    Author     : jovidic
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>A Komunikacija</title>
        <script type="text/javascript" src="files/scripts/shCore.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushBash.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushCpp.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushCSharp.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushCss.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushDelphi.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushDiff.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushGroovy.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushJava.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushJScript.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushPhp.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushPlain.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushPython.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushRuby.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushScala.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushSql.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushVb.js"></script>
        <script type="text/javascript" src="files/scripts/shBrushXml.js"></script>
        <link type="text/css" rel="stylesheet" href="files/styles/shCore.css"/>
        <link type="text/css" rel="stylesheet" href="files/styles/shThemeDefault.css"/>
        <link type="text/css" rel="stylesheet" href="files/styles/josip.css"/>
        <script type="text/javascript">
            SyntaxHighlighter.config.clipboardSwf = 'files/scripts/clipboard.swf';
            SyntaxHighlighter.all();
        </script>
    </head>
    <body>
        <h1>Komunikacija na strani aplikacije A</h1>
        <br>
        <div class="wrap">
            <div class="xml">
                <h2>Aplikacija A šalje na Web servis zahtjev</h2>

                <p> Aplikacija A pruža potrebne podatke Web servisu koji na temelju istih kreira zaglavlje i šalje aplikaciji B:</p>
                <br>
                <h3>SOAP poruka eb:Elementi</h3>
                <ul class="lista">
                    <li>Timestamp - ${timestamp}</li>
                    <li>From - ${from}</li>
                    <li>To -${to}</li>
                    <li>CPAID - ${cpaid}</li>
                    <li>ConversationID - ${convid}</li>
                    <li>Service -${service}</li>
                    <li>Action - ${action}</li>
                    <li>Data - ${data}</li> 
                    <li>MessageId - ${id}</li>  
                </ul>
                <br>

            </div>

            <div class="xml">
                <h2>Aplikacija A je primila poruku od aplikacije B</h2>

                <p> Aplikacija A je uspješno primila podatke poslane od strane aplikacije b:</p>
                <br>
                <h3>SOAP poruka eb:Elementi</h3>
                <ul class="lista">
                    <li>Timestamp - ${timestamp1}</li>
                    <li>From - ${from1}</li>
                    <li>To -${to1}</li>
                    <li>CPAID - ${cpaid1}</li>
                    <li>ConversationID - ${convid1}</li>
                    <li>Service -${service1}</li>
                    <li>Action - ${action1}</li>
                    <li>Data - ${data1}</li>  
                    <li>Description - ${description1}</li>  
                    <li>MessageId - ${id1}</li>  
                </ul>
                <br>
                <h3>SOAP prilog trans:Elementi</h3>
                <ul class="lista">
                    <li>To - ${toR}</li>
                    <li>From - ${fromR}</li>
                    <li>Cost- ${costR}</li>
                    <li>Date - ${dateR}</li> 
                </ul>
                <br>
                <pre class="brush: c-sharp;">
                    ${msg}
                </pre>
                <br>
            </div></div>

    </body>
</html>
