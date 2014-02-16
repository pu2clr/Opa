<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Upload File</title>
</head>
<body>
    <form action="FileUpload" method="post" enctype="multipart/form-data">
        <table>
            <tr>
                <td>Select File : </td>
                <td><input  name="file" type="file"/> </td>
            </tr>
            <tr>
                <td>Enter Filename : </td>
                <td><input type="text" name="photoname" size="20"/> </td>
            </tr>
        </table>
        <p/>
        <input type="submit" value="Upload File"/>
    </form>
 </body>
</html>
