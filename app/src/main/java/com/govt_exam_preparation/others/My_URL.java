package com.govt_exam_preparation.others;

/**
 * Created by wscubetech on 9/3/16.
 */
public class My_URL {

    public static String base_url = "http://ypeexam.com/api/";
    public static String image_url = "http://ypeexam.com/uploads/";


    //   http://www.wscubetechapps.in/mobileteam/exam_preparation_app/api/login.php?user_phone=
    public static String login = base_url + "login.php?";

    // send OTP
    //  http://www.wscubetechapps.in/mobileteam/exam_preparation_app/api/seandotp.php?flag=&mobile_number=
    public static final String SendOTP = base_url + "seandotp.php?";

    //    View Job List
//    http://www.wscubetechapps.in/mobileteam/exam_preparation_app/api/job.php
    public static String job_list = base_url + "job.php";

    //    http://www.wscubetechapps.in/mobileteam/exam_preparation_app/api/city.php
    public static String city_list = base_url + "city.php";

//    View Coaching BY City-ID
//    http://www.wscubetechapps.in/mobileteam/exam_preparation_app/api/coaching.php?city_id=

    public static String coaching_center_list = base_url + "coaching.php?";

    public static String coaching_center_list1 = base_url + "view_coaching_range.php?";

//    View Coaching Gallery Images BY Coaching-ID
//    http://www.wscubetechapps.in/mobileteam/exam_preparation_app/api/coaching_gallery.php?coaching_id=

    public static String gallery_list = base_url + "coaching_gallery.php?";

//    View Comprehension text By Date
//    http://www.wscubetechapps.in/mobileteam/exam_preparation_app/api/question_text.php?today_date=12/05/2016

    public static String quiz = base_url + "question_text.php?";

//    View Coaching All Info BY Coaching-ID
//    http://www.wscubetechapps.in/mobileteam/exam_preparation_app/api/coaching_info.php?coaching_id=

    public static String coaching_info = base_url + "coaching_info.php?coaching_id=";

    public static final String view_notification = base_url + "view_noti.php";


    public static String Web_view_url = "";


    public static String add_device_id = base_url + "add_did.php?";
//    http://www.wscubetechapps.in/mobileteam/exam_preparation_app/api/add_did.php?user_id=&did=&flag=


    //new work
    public static final String viewExams = base_url + "view_exam.php";

    public static final String viewSubjects = base_url + "view_exam_subject.php?exam_id=";

    public static final String registration = base_url + "sign_up.php?";
    //user_name=&user_gender=&user_email=&user_phone=&user_city=&user_address=&selected_exam_id=&selected_subject_ids=&selected_coupon_id=

    public static final String viewCoupons = base_url + "view_coupon.php";

    public static final String viewQuestionOrTextStatus = base_url + "question_date.php?";
    //exam_id=&subject_id=
    //output:
    /*result: 1,
    msg: {
        quiz: "Yes",
        theory: "No",
        status: 2
    }*/
    //status=2 //Quiz
    //status=1 //theory

    public static final String viewQuestionOrText = base_url + "question_text.php?status=";
    //&exam_id=&subject_id=
    //status=2 //Quiz
    //status=1 //theory
    //&date=23/06/2016

    public static final String viewAboutUs = base_url + "about_us.php";

    public static final String sendOtpLogin = base_url + "seandotp.php?";
    //flag=&mobile_number=
    // flag = 1 => sign up,    2=> login
}
