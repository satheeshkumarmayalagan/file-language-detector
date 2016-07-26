angular.module("languageDetectorApp", [])
		 .run(function($rootScope) {
			$rootScope.socialInfo = {
				"emailID" : "bluepencilliteraryservices@gmail.com",
				"facebookID": "" ,
				"tritterID": ""
			};
		 })
         .directive('fileModel', ['$parse', function ($parse) {
        	    return {
        	        restrict: 'A',
        	        link: function(scope, element, attrs) {
        	            var model = $parse(attrs.fileModel);
        	            var modelSetter = model.assign;
        	            
        	            element.bind('change', function(){
        	                scope.$apply(function(){
        	                    modelSetter(scope, element[0].files[0]);
        	                });
        	            });
        	        }
        	    };
        	}])
		.controller('languageDetectController',
			[ '$scope', '$rootScope', 'fileUpload', 'displayMessageService', 
			  	function($scope, $rootScope, fileUpload, displayMessageService) {
				$scope.contact = {};
				
				$scope.disableContactUSForm = false;
				$scope.showSpinner = false;
				
				$scope.uploadFile = function() {
					var file = $scope.myFile;
					if(!(angular.isUndefined(file) || file === null)) {
						if(this.checkFileSize(file)) {
							var uploadUrl = "detectLanguage"; 
							fileUpload.uploadFileToUrl(file, uploadUrl, $scope.uploadFileCallback);
						} else {
							$scope.showSpinner = false;
							$scope.disableContactUSForm = false;
							
							displayMessageService.showErrorMessage("File size should be less than 2MB and should be of .txt, .doc, .pfd file");
						}
					}
				};

				$scope.checkFileSize = function(file) {
					if(file.size <= 2097152 && (file.type == "text/plain" || file.type == "application/msword" || file.type == "application/pdf")) {
						return true;
					} else {
						return false;
					}
				};
				
				$scope.uploadFileCallback = function(data) {
					if(data.status == "success") {
						displayMessageService.showSuccessMessage("File language is - " + data.language);
					} else {
						displayMessageService.showErrorMessage("The file could not be uploaded. Please check the file size is less than 2MB, is .txt, .doc, .pfd file and a valid language");
					}
				};
				
				$scope.detectLanguageForTheFile = function() {
					$scope.disableContactUSForm = true;
					$scope.showSpinner = true;

					displayMessageService.showErrorMessage("Please wait..");
					var file = $scope.myFile;
					if(!(angular.isUndefined(file) || file === null)) {
						this.uploadFile();
					} else {
						var data = "";
						displayMessageService.showErrorMessage("Please select a file..");
					}		
				};
				
				$scope.fileNameChanged = function(element) {
	            	$scope.contact.fileuploaduri = element.value;
	            	$scope.$apply(function(scope) {
	                });         	
	            };				
		}])
        .service('fileUpload', [ '$http', function($http) {
			this.uploadFileToUrl = function(file, uploadUrl, callback) {
				var fd = new FormData();
				fd.append('file', file);
				$http.post(uploadUrl, fd, {
					transformRequest : angular.identity,
					headers : {
						'Content-Type' : undefined
					}
				}).success(function(data) {
					callback(data);
				}).error(function(data) {
					callback(data);
				});
			}
		} ])
		.service('displayMessageService', function() {
		    this.showSuccessMessage = function (message) {
				this.showMessage('success-message-area', message);
		    }
		    this.showErrorMessage = function (message) {
				this.showMessage('error-message-area', message);
		    }
			this.showMessage = function(fldName, message) {
				// Hide all elements.
				this.clearMessageArea();

				var elmShow = document.getElementById(fldName);
				try {
					elmShow.innerHTML = message;
					elmShow.style.display = "block";
				} catch (e) {
				}
			};
			this.clearMessageArea = function() {

				var elmMsgArea = document.getElementById('message-area');

				// Hide all elements.
				var nodeChildren = elmMsgArea.childNodes;
				for (var i = 0; i < nodeChildren.length; i++) {
					var nodeChild = nodeChildren[i];
					try {
						if (nodeChild.nodeName != 'DIV')
							continue;
						if (nodeChild.getAttribute('role')) {
							nodeChild.style.display = 'none';
						}
					} catch (e) {
					}
				}

				// Display div tag contents
				try {
					elmMsgArea.style.display = 'block';
				} catch (e) {
				}
			};

			this.showSpinner = function(idSpinner) {
				var elmSpinner = document.getElementById(idSpinner);
				if (elmSpinner != null) {
					elmSpinner.style.display = 'inline-block';
				}
			};

			this.hideSpinner = function(idSpinner) {
				var elmSpinner = document.getElementById(idSpinner);
				if (elmSpinner != null) {
					elmSpinner.style.display = 'none';
				}
			};		    
		})
		;