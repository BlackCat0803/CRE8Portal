/**
 * isAsciiPrintable
 * @param str
 * @returns {Boolean}
 */ 
//For each char occurance, check for the regex validation
 function isAsciiPrintable(str) {
 	try{
	      if (str == null) {
	          return false;
	      }
	      var sz = str.length;
	      for (var i = 0; i < sz; i++) {
	      
	       	  //alert(str.charAt(i)+"==========="+isAsciiPrintable2(str.charAt(i)))
	       	  
	          if (checkIsAsciiPrintable(str.charAt(i)) == false) {
	              return false;
	          }
	      }
      }catch(e)
     	{
     		//alert(e)
     		return true;
     	}
      	return true;
  }
 /**
  * checkIsAsciiPrintable
  * @param ch
  * @returns {Boolean}
  */
  //Regex Validation for password, check for Upper case/Lower case/Number/Non-alphanumeric (~!@#$%^*&;?.+_)
 function checkIsAsciiPrintable(ch) {
 
 	 try{
	 	  re1 = /[0-9]/;
	 	  re2 = /[a-z]/;
	 	  re3 = /[A-Z]/;
	 	  re4 = /[~!@#$%^*&;?.+_]/;
		  if(re1.test(ch) || re2.test(ch) || re3.test(ch) || re4.test(ch))
		  	return true;
		  else
	      	return false;
      	}catch(e)
      	{
	      	//alert(e)
      		return true;
      	}
      	return true;
  }

/**
 * checkSequence
 * @param oriString
 * @param strTobeMatched
 * @param seqLimit
 * @returns {Boolean}
 */
function checkSequence(oriString,strTobeMatched,seqLimit) {
				var countRet=-1;
				var strLen=0;
				var strtoBeSearched="";
				var alreadyExists=false;
				
				try {
					//alert("oriString ==="+oriString);
					/*alert("strTobeMatched ==="+strTobeMatched);
					alert("seqLimit ==="+seqLimit);*/
					strLen=strTobeMatched.length;
					for (var i = 0; i < strLen; i++) {
						if(i<strLen-(seqLimit)){
							  strtoBeSearched=strTobeMatched.substring(i, i+seqLimit+1);
							  //alert("strtoBeSearched ========"+strtoBeSearched);
							  //countRet = StringUtils.countMatches(oriString, strtoBeSearched);
							  countRet = oriString.indexOf(strtoBeSearched); 
							 //alert("countRet ======"+countRet);
							  if(countRet>=0)
							  {
								  alreadyExists=true;
								  break;
							  }
					 
						}
					}
				} catch (e) {
					//alert("checkSequence =========="+e)
				}
				return alreadyExists;
}
/**
 * getPasswordCategoryCount
 */
function getPasswordCategoryCount(password)
{
	var count=0;
	var re = /[0-9]/;
    if(re.test(password))
    {
	    //msg+="Password must contain at least one number (0-9)! <br>"; 
	    //flag1=true;
	    count++;
    } 
  	re = /[a-z]/;
   	if(re.test(password))
    {
	    //msg+="Password must contain at least one lowercase letter (a-z)! <br>";
	    //flag2=true;
	    count++;
    }
    re = /[A-Z]/; 
    if(re.test(password))
    {
        //msg+="Password must contain at least one uppercase letter (A-Z)! <br>";
        //flag3=true;
        count++;
    }
     //Apr 15,2015-Non-alphabetic characters: ~!@#$%^*&;?.+_
    //re = /[!@#$%^&*]/;
    re = /[~!@#$%^*&;?.+_]/;
    if(re.test(password))
    {
       // msg+="Password must contain at least Non-alphanumeric (~!@#$%^*&;?.+_)! <br>";
        //flag4=true;
        count++;
    }
    return count;
}

var error_passwordminmax=$("#error_passwordminmax").val();
var error_categoriesnotmatch=$("#error_categoriesnotmatch").val();
var error_categoriesnotmatch=$("#error_categoriesnotmatch").val();
var error_temporarypassword=$("#error_temporarypassword").val();

var passwordminlength=$("#passwordminlength").val();
var passwordmaxlength=$("#passwordmaxlength").val();
error_passwordminmax=error_passwordminmax.replace(/#minPwdLen#/g,passwordminlength);
error_passwordminmax=error_passwordminmax.replace(/#maxPwdLen#/g,passwordmaxlength);
error_categoriesnotmatch=error_categoriesnotmatch.replace(/#minPwdLen#/g,passwordminlength);
error_categoriesnotmatch=error_categoriesnotmatch.replace(/#maxPwdLen#/g,passwordmaxlength);

//The rules that a secure password need to follow
var PASSWORD_RULES = [
	{
		name: 'isAsciiPrintable',
	    message:  $("#error_asciiprintnotmatch").val()
	   
	},   
	{
        name: 'temporary',
        message: error_temporarypassword
       
    },
   {
        name: 'length',
        message: error_passwordminmax
       
    },
	{
	        name: 'category',
	        message: error_categoriesnotmatch
	        
    },

    {
        name: 'sequence',
        message:  $("#error_sequencematch").val()
       
    }
   
];
/**
 * checkHipaaValidation
 * @param password
 * @param fullname
 * @param accountname
 * @returns {String}
 */
function checkHipaaValidation(password,fullname,accountname)
{
	var hipaaValidateMsg="";
	try {
		fullname=fullname.toLowerCase();
		fullname = fullname.replace(/\s+/g, '');
		accountname=accountname.toLowerCase();
		accountname = accountname.replace(/\s+/g, '');
		 
		/*if (!isAsciiPrintable(password)) {
			hipaaValidateMsg = PASSWORD_RULES[0].message;
		}
		else if (password.toLowerCase().indexOf("temp")==0) {
			hipaaValidateMsg = PASSWORD_RULES[1].message;
		}
		else if (password.length < passwordminlength
				|| password.length > passwordmaxlength) {
			hipaaValidateMsg = PASSWORD_RULES[2].message;
		} else if (getPasswordCategoryCount(password) < 3) {
			hipaaValidateMsg = PASSWORD_RULES[3].message;
		}
		else {
			var newpassword = password;
			newpassword = newpassword.toLowerCase();
			if (checkSequence(fullname, newpassword, 2)
					|| checkSequence(accountname, newpassword, 2)) {
				hipaaValidateMsg = PASSWORD_RULES[4].message;
			}

		}*/
		// at least one number, one lowercase and one uppercase letter
		// at least six characters
		var re = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}/;
		if(re.test(password))
			return hipaaValidateMsg;
		else
			return "Password should be of minimum length 6 and contains atleast 1 Capital letter and 1 numeric character."
	} catch (e) {
		alert(e)
	}
	return hipaaValidateMsg;

}
/* Function to ensure whether numbers can be allowed in the fields, allowed special characters, Whether numbers/special characters can lead */

function stripHipaaSpChars(allowNumbers,allowedSpChars,numCanLead,fieldName) {
    var fieldValue=fieldName.value;
    var canBeNumbers=allowNumbers;
    var corrected="";
    var spChars=allowedSpChars;
    var spCharsLength=spChars.length;
    var numCanLead=numCanLead;

    for (var j=0;j<=fieldValue.length;j++) {
    	//65 – 90 – capital letters 'A' to 'Z'
    	//97 –122 – small letters 'a' to 'z'
        if ((fieldValue.charCodeAt(j) >=65 && fieldValue.charCodeAt(j)

                <= 90) ||  (fieldValue.charCodeAt(j) >= 97 &&

                            fieldValue.charCodeAt(j) <= 122)) {

            corrected=corrected+fieldValue.charAt(j);

        } else if (fieldValue.charCodeAt(j) >= 48 && fieldValue.charCodeAt(j) <= 57) {
        	//48 – 57 – numbers '0'-'9'
            if (canBeNumbers) {
                if (j==0 || corrected=="") {
                    if (numCanLead)
                        corrected=corrected+fieldValue.charAt(j);
                } else
                    corrected=corrected+fieldValue.charAt(j);
            }
        } else if (spChars.length>0) {
        	//To allow the list of special charaters at the beginning of the word
            for (var spIndex=0;spIndex<spCharsLength;spIndex++) {
                if (spChars.charAt(spIndex)==fieldValue.charAt(j) && 
                        (fieldValue.indexOf(fieldValue.charAt(j))!=-1)) {
                    corrected=corrected+fieldValue.charAt(j);
                }
            }
           
        }
    }
    fieldName.value=corrected;
}//End of Function stripSpChars

/* Function to ensure whether numbers can be allowed in the fields, allowed special characters, Whether numbers can lead */

function stripSpChars(allowNumbers,allowedSpChars,numCanLead,fieldName) {
    var fieldValue=fieldName.value;
    var canBeNumbers=allowNumbers;
    var corrected="";
    var spChars=allowedSpChars;
    var spCharsLength=spChars.length;
    var numCanLead=numCanLead;

    for (var j=0;j<=fieldValue.length;j++) {
    	//65 – 90 – capital letters 'A' to 'Z'
    	//97 –122 – small letters 'a' to 'z'
        if ((fieldValue.charCodeAt(j) >=65 && fieldValue.charCodeAt(j)

                <= 90) ||  (fieldValue.charCodeAt(j) >= 97 &&

                            fieldValue.charCodeAt(j) <= 122)) {

            corrected=corrected+fieldValue.charAt(j);

        } else if (fieldValue.charCodeAt(j) >= 48 && fieldValue.charCodeAt(j) <= 57) {
        	//48 – 57 – numbers '0'-'9'
            if (canBeNumbers) {
                if (j==0 || corrected=="") {
                    if (numCanLead)
                        corrected=corrected+fieldValue.charAt(j);
                } else
                    corrected=corrected+fieldValue.charAt(j);
            }
        } else if (spChars.length>0) {
            if (!(j==0 || corrected=="")) {
                for (var spIndex=0;spIndex<spCharsLength;spIndex++) {
                    if (spChars.charAt(spIndex)==fieldValue.charAt(j) &&
                            (fieldValue.indexOf(fieldValue.charAt(j))!=0)) {
                        corrected=corrected+fieldValue.charAt(j);
                    }
                }
            }
        }
    }
    fieldName.value=corrected;
}//End of Function stripSpChars