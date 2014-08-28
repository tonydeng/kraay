function beginSelect(elem){
    if(elem.className == "btn"){
        elem.className = "btn btnhover"
        elem.onmouseup = function(){
            this.className = "btn"
        }
    }
    var ul = elem.parentNode.parentNode;
    var li = ul.getElementsByTagName("li");
    var selectArea = li[li.length-1];
    if(selectArea.style.display == "block"){
        selectArea.style.display = "none";
    }
    else{
        selectArea.style.display = "block";
        mouseoverBg(selectArea);
    }
    }
    function mouseoverBg(elem1){
        var input = elem1.parentNode.getElementsByTagName("input")[0];
        var p = elem1.getElementsByTagName("p");
        var pLength = p.length;
        for(var i = 0; i < pLength; i++){
            p[i].onmouseover = showBg;
            p[i].onmouseout = showBg;
            p[i].onclick = postText;
        }
        function showBg(){
            this.className == "hover"?this.className = " ":this.className = "hover";
        }
        function postText(){
            var selected = this.innerHTML;
            var value = this.getAttribute('val');
            input.setAttribute("value",value);
            elem1.style.display = "none";
        }
    }