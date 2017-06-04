/**
 * Created by remi on 17/01/15.
 */
(function () {

    console.log("HELLO");
    /**
     * A simple function to display file informations
     */
    function showFileInfo(file) {
        console.log("name : " + file.name);
        console.log("size : " + file.size);
        console.log("type : " + file.type);
        console.log("date : " + file.lastModified);
    }

    var fileInput1 = document.querySelector('#simplefile');
    fileInput1.addEventListener('change', function () {
        var fileSelected = this.files[0];
        showFileInfo(fileSelected);
    }, false);

}());
