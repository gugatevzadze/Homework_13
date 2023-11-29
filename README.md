# Homework_13
ანდროიდ ბუთქემფის დავალება 13

დავალების პირობა:

გვერდზე ვიზუალური კომპონენტების გამოჩენა დამოკიდებულია სერვერიდან მიღებულ ინფორმაციაზე. ვებ სერვისი არ არის დასრულებული. დასრულებამდე გვევალება შევქმანთ ფუნქციონალი რომელიც მარტივად მოერგება სერვერიდან მიღებულ ინფორმაციას. დეველოპერმა მიახლოებითი სტრუქტურა მოგვაწოდა (გვაქვს ფილდის ორი ტიპი “input” და chooser(აქვე ვიცით რომ ყველა ამ ტიპის ფილდი არასავალდებულოა) ), რომელიც შეიძლება ასე გამოიყურებოდეს 
[ 
[ 
{ 
"field_id":1, 
"hint":"UserName", 
"field_type":"input", 
"keyboard":"text", 
"required":false, 
"is_active":true, 
"icon":"https://jemala.png" 
}, 
{ 
"field_id":2, 
"hint":"Email", 
"field_type":"input", 
"required":true, 
"keyboard":"text", 
"is_active":true, 
"icon":"https://jemala.png" 
}, 
{ 
"field_id":3, 
"hint":"phone", 
"field_type":"input", 
"required":true, 
"keyboard":"number", 
"is_active":true, 
"icon":"https://jemala.png" 
} 
], 
[ 
{ 
"field_id":4,
"hint":"FullName", 
"field_type":"input", 
"keyboard":"text", 
"required":true, 
"is_active":true, 
"icon":"https://jemala.png" }, 
{ 
"field_id":14, 
"hint":"Jemali", 
"field_type":"input", 
"keyboard":"text", 
"required":false, 
"is_active":true, 
"icon":"https://jemala.png" }, 
{ 
"field_id":89, 
"hint":"Birthday", 
"field_type":"chooser", 
"required":false, 
"is_active":true, 
"icon":"https://jemala.png" }, 
{ 
"field_id":898, 
"hint":"Gender", 
"field_type":"chooser", 
"required":"false", 
"is_active":true, 
"icon":"https://jemala.png" } 
] 
]

ფილდების რაოდენობა არ არის განსაზღვრული წინასწარ, დამოკიდებულია კონფიგურაციაზე. თვენი ამოცანაა გამოაჩინოთ ვიზაულური კომპონენტები ყალბი ინფომრაციის საფუძველზე. “Register” ღილაკზე დაჭერის შემდეგ უნდა ამოვიღოთ შესაბამისი პარამეტრი ვიზუალური კომპონენტიდან და განთავსდეს კონტეინერში Key value პრინციპით. key არის ფილდის “field_id”. შემოწმება უნდა მოხდეს სერევრიდან მიღებული ინფორმაციის საფუძვლეზე, მაგალითად თუ არ არის შევსებული ფილდი და არის სავალდებულო ეკრანზე გამოვიტანოთ ტექსტური შეტყობინება“არ არის შევსბული ფილდი (მიუთითეთ ფილდის hint)”.

კომენტარები:
ატვირთული დავალება არ არის ბოლომდე შესრულებული. chooser ტიპის ინფუთის კოდის დაწერა ვეღარ მოვასწარი, მთლიანი დავალების არ ატვირთვას ვამჯობინე რომ რაღაც ნაწილი შემეწირა მსხვერპლად. 
რაც წერია არც იმით არ ვამაყობ, ვეცდები შემდეგ დავალებებში გამოვასწორო. 
