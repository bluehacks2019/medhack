// Initialize Firebase
  var config = {
    apiKey: "AIzaSyDebzr-USPZej91McvNRC5KeHQyg1SC2FY",
    authDomain: "m3dh4ck.firebaseapp.com",
    databaseURL: "https://m3dh4ck.firebaseio.com",
    projectId: "m3dh4ck",
    storageBucket: "m3dh4ck.appspot.com",
    messagingSenderId: "749785979787"
  };
  firebase.initializeApp(config);

   // // Get the current date
   // sessionStorage.setItem("test","");
   // alert(sessionStorage.getItem("teest"));
    var getDate = new Date();
    var getMonth = (getDate.getMonth()+1);
    var getDay = getDate.getDate();
    var getYear = getDate.getFullYear();
    var concat_date = getMonth + "/" + getDay + "/" + getYear;

    // Get the current hours
    var getHours = getDate.getHours();
    var getMinutes = getDate.getMinutes();
    var concat_time = getHours + ":" + getMinutes;

  $(document).ready(function(e){

    //Initialize on LOAD
    getAvgUsers();
    getFacts();
    getContacts();
    getReasonOfSmile();
    getAvgMood();
    homecharts();
    getStrugglingPeople();

    // Get Average number of Users
    function getAvgUsers(){
         var firebasedb = firebase.database().ref("Users");
             firebasedb.on('value', function(snapshot){
             var users = snapshot.numChildren();
             if(snapshot.exists()){
               var content ="";
               snapshot.forEach(function(data){
                 var key = data.key;
                 content = users;
                });
                    $("#num_users").text(content);
                 }
             });
           }
    function getFacts(){
       var firebasedb = firebase.database().ref("Facts");
           firebasedb.on('value', function(snapshot){

               if(snapshot.exists()){
                   var content ="";
                   snapshot.forEach(function(data){
                       var key = data.key;
                       var val = data.val();
                      content += "<tr><td width='90%'>"+val.Description+"</td><td width='10%'><form><input type='hidden' value='"+key+"' id='txt_fact'><button type='button' class='btn btn-danger float-right' onclick='delFacts();'><i class='fa fa-trash'></i></form></td></tr>";
                   });
                   $("#facts_value").append(content);
               }
           });
         }
      function getContacts(){
       var firebasedb = firebase.database().ref("medicalContacts");
           firebasedb.on('value', function(snapshot){

               if(snapshot.exists()){
                   var content ="";
                   snapshot.forEach(function(data){
                       var key = data.key;
                       var val = data.val();
                      content += '<tr><th scope="row">'+val.position+'</th><td>'+val.contactName+'</td><td>'+val.contactNumber+'</td><td>Latitude: '+val.Latitude+'<br>Longitude: '+val.Longitude+'</td><td><form><input type="hidden" id="contact_key" value="'+key+'"><button type="button" class="btn btn-danger" onclick="delContacts();"><i class="fa fa-trash"></i></button></form></td></tr>';
                   });
                   $("#view_contacts").append(content);
               }
           });
         }
      });

      function getReasonOfSmile(){
       var firebasedb = firebase.database().ref("userThoughts");
           firebasedb.on('value', function(snapshot){

               if(snapshot.exists()){
                   var txt_reason ="";
                   snapshot.forEach(function(data){
                       var key = data.key;
                       var val = data.val();
                       var keyofuser = val.userKey;
                       txt_reason += '<div class="col-lg-6"><div class="block margin-bottom-sm"><div class="title"><h1><i class="fa fa-smile-o"></i><i class="fa fa-smile-o"></i><i class="fa fa-smile-o"></i></h1></div><div class="table-responsive"><h5>Reason of smile:</h5><p>'+val.status+'</p></div></div></div>';
                       });
                   $("#txt_reasonofsmile").append(txt_reason);
               }
           });
         }
         function getStrugglingPeople(){
       var firebasedb = firebase.database().ref("mood");
           firebasedb.on('value', function(snapshot){

               if(snapshot.exists()){
                   var content ="";
                   var i=0;
                   snapshot.forEach(function(data){

                       var key = data.key;
                       var val = data.val();
                       var keys = Object.keys(snapshot.val())[i];

                       var sliced = keys.slice(21);
                       
                       if (val.m4 == "0") {

                       }
                       else if(val.m5 == "0"){

                       }
                       else{
                        content += '<div class="col-lg-6"><div class="block margin-bottom-sm"><div class="title"><strong>'+sliced+'</strong><h5>Current mood: Sad</h5></div><div class="table-responsive"><small>Message:</small><form class="form-group"><textarea class="form-control" rows="5" style="resize: none;"></textarea><br><button class="btn btn-info float-right">Message</button></form></div></div></div>';

                       }
                       i++;
                     });
                   $("#struggling-people").append(content);
               }
           });
         }
      function getAvgMood(){
       var firebasedb = firebase.database().ref("mood");
           firebasedb.on('value', function(snapshot){
            var avgmood = snapshot.numChildren();
               if(snapshot.exists()){
                   var content ="";
                   snapshot.forEach(function(data){
                       var key = data.key;
                       var val = data.val();

                       // c = converted
                       var c1 = parseInt(val.m1);
                       var c2 = parseInt(val.m2);
                       var c3 = parseInt(val.m3);
                       var c4 = parseInt(val.m4);
                       var c5 = parseInt(val.m5);


                       content += (c1+c2+c3+c4+c5)/10;
                     });
                   $("#avg_mood").text(parseInt(content));
               }
           });
         }    

      $("#add_medical_contact").click(function(e){
         var firebasedb = firebase.database().ref("medicalContacts");
         var rand = Math.random().toString(36).substr(2, 10);

          var getUrlParameter = function getUrlParameter(sParam) {
          var sPageURL = window.location.search.substring(1),
              sURLVariables = sPageURL.split('&'),
              sParameterName,
              i;

              for (i = 0; i < sURLVariables.length; i++) {
                  sParameterName = sURLVariables[i].split('=');

                  if (sParameterName[0] === sParam) {
                      return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
                   }
                 }
              };
            var lat = getUrlParameter('lat');
            var lng = getUrlParameter('lng');
            var position = $("#add_contact_position").val();
            var contactName = $("#add_contact_name").val();
            var contactNumber = $("#add_contact_number").val();

            firebasedb.push({
             "Latitude":lat,
             "Longitude":lng,
             "position":position,
             "contactName":contactName,
             "contactNumber": contactNumber
         });
             alert("Contact Added");
             window.location.reload();
        });

        $("#btn_addfact").click(function(e){
         var firebasedb = firebase.database().ref("Facts");
            var txt_fact = $("#add_fact").val();
            firebasedb.push({
             "Description":txt_fact
         });
             alert("Added");
             window.location.reload();
        });

        $("#btn_announcemood").click(function(e){
         var firebasedb = firebase.database().ref("Announcement");
            var mood_type = $("#mood_type").val();
            var txt_announcement = $("#txt_announcement").val();
            firebasedb.push({
             "mood_type":mood_type,
             "announcement":txt_announcement
         });
             alert("Added");
             window.location.reload();
        });

        function delFacts(){
         var firebasedb = firebase.database().ref("Facts");
         var key = $("#txt_fact").val();
         if (confirm("Delete record?")) {
           firebasedb.child(key).remove();
         }
         window.location.reload();
       }
       function delContacts(){
         var firebasedb = firebase.database().ref("medicalContacts");
         var key = $("#contact_key").val();
         if (confirm("Delete record?")) {
           firebasedb.child(key).remove();
         }
         window.location.reload();
       }



       function homecharts(){

        var firebasedb = firebase.database().ref("mood");
           firebasedb.on('value', function(snapshot){
            var avgmood = snapshot.numChildren();
               if(snapshot.exists()){
                   var content ="";
                   snapshot.forEach(function(data){
                       var key = data.key;
                       var val = data.val();
                       var keyofuser = val.userKey;

                       // c = converted
                       var c1 = parseInt(val.m1);
                       var c2 = parseInt(val.m2);
                       var c3 = parseInt(val.m3);
                       var c4 = parseInt(val.m4);
                       var c5 = parseInt(val.m5);


                       content = (c1+c2+c3+c4+c5)/5;
                     });
                   // $("#avg_mood").text(parseInt(content));
               }
           });

      Chart.defaults.global.defaultFontColor = '#75787c';
      var legendState = true;
      if ($(window).outerWidth() < 576) {
          legendState = false;
      }

      var LINECHART = $('#lineCahrt');
      var myLineChart = new Chart(LINECHART, {
          type: 'line',
          options: {
              scales: {
                  xAxes: [{
                      display: true,
                      gridLines: {
                          display: false
                      }
                  }],
                  yAxes: [{
                      ticks: {
                          max: 100,
                          min: 0
                      },
                      display: true,
                      gridLines: {
                          display: false
                      }
                  }]
              },
              legend: {
                  display: legendState
              }
          },
          data: {
              labels: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
              datasets: [
                  {
                      label: "Great",
                      fill: true,
                      lineTension: 0.2,
                      backgroundColor: "transparent",
                      borderColor: '#2ecc71',
                      pointBorderColor: '#2ecc71',
                      pointHoverBackgroundColor: '#27ae60',
                      borderCapStyle: 'butt',
                      borderDash: [],
                      borderDashOffset: 0.0,
                      borderJoinStyle: 'miter',
                      borderWidth: 2,
                      pointBackgroundColor: "#fff",
                      pointBorderWidth: 5,
                      pointHoverRadius: 5,
                      pointHoverBorderColor: "#fff",
                      pointHoverBorderWidth: 2,
                      pointRadius: 1,
                      pointHitRadius: 0,
                      data: [18, 27, 20, 35, 30, 40, 33, 25, 39],
                      spanGaps: false
                  },
                  {
                      label: "Good",
                      fill: true,
                      lineTension: 0.2,
                      backgroundColor: "transparent",
                      borderColor: "#3498db",
                      pointBorderColor: '#3498db',
                      pointHoverBackgroundColor: "#2980b9",
                      borderCapStyle: 'butt',
                      borderDash: [],
                      borderDashOffset: 0.0,
                      borderJoinStyle: 'miter',
                      borderWidth: 2,
                      pointBackgroundColor: "#fff",
                      pointBorderWidth: 5,
                      pointHoverRadius: 5,
                      pointHoverBorderColor: "#fff",
                      pointHoverBorderWidth: 2,
                      pointRadius: 1,
                      pointHitRadius: 10,
                      data: [25, 17, 28, 25, 33, 27, 30, 33, 27],
                      spanGaps: false
                  },
                  {
                      label: "Meh",
                      fill: true,
                      lineTension: 0.2,
                      backgroundColor: "transparent",
                      borderColor: "#f1c40f",
                      pointBorderColor: '#f1c40f',
                      pointHoverBackgroundColor: "#f39c12",
                      borderCapStyle: 'butt',
                      borderDash: [],
                      borderDashOffset: 0.0,
                      borderJoinStyle: 'miter',
                      borderWidth: 2,
                      pointBackgroundColor: "#fff",
                      pointBorderWidth: 5,
                      pointHoverRadius: 5,
                      pointHoverBorderColor: "#fff",
                      pointHoverBorderWidth: 2,
                      pointRadius: 1,
                      pointHitRadius: 10,
                      data: [45, 27, 21, 37, 82, 29, 11, 32, 76],
                      spanGaps: false
                  },
                  {
                      label: "Bad",
                      fill: true,
                      lineTension: 0.2,
                      backgroundColor: "transparent",
                      borderColor: "#e67e22",
                      pointBorderColor: '#e67e22',
                      pointHoverBackgroundColor: "#d35400",
                      borderCapStyle: 'butt',
                      borderDash: [],
                      borderDashOffset: 0.0,
                      borderJoinStyle: 'miter',
                      borderWidth: 2,
                      pointBackgroundColor: "#fff",
                      pointBorderWidth: 5,
                      pointHoverRadius: 5,
                      pointHoverBorderColor: "#fff",
                      pointHoverBorderWidth: 2,
                      pointRadius: 1,
                      pointHitRadius: 10,
                      data: [29, 12, 75, 50, 42, 2, 97,29, 28],
                      spanGaps: false
                  },
                  {
                      label: "Awful",
                      fill: true,
                      lineTension: 0.2,
                      backgroundColor: "transparent",
                      borderColor: "#e74c3c",
                      pointBorderColor: '#e74c3c',
                      pointHoverBackgroundColor: "#c0392b",
                      borderCapStyle: 'butt',
                      borderDash: [],
                      borderDashOffset: 0.0,
                      borderJoinStyle: 'miter',
                      borderWidth: 2,
                      pointBackgroundColor: "#fff",
                      pointBorderWidth: 5,
                      pointHoverRadius: 5,
                      pointHoverBorderColor: "#fff",
                      pointHoverBorderWidth: 2,
                      pointRadius: 1,
                      pointHitRadius: 10,
                      data: [81, 2, 9, 10, 75, 22, 91, 61, 50],
                      spanGaps: false
                  }
              ]
          }
      });
  }