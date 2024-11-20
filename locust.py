from locust import HttpUser, TaskSet, task, between, SequentialTaskSet
import json
import random

users = [
    {"email": "user3@quizapp.com", "password": "adminuser"},
    {"email": "user4@quizapp.com", "password": "adminuser"},
    {"email": "user5@quizapp.com", "password": "adminuser"},
    {"email": "user10@quizapp.com", "password": "an93zDz_#O0"},
    {"email": "user11@quizapp.com", "password": "7Y?PMau2XJ#"},
    {"email": "user12@quizapp.com", "password": "NWiy0=Jorsy"},
    {"email": "user13@quizapp.com", "password": "GftCX?^msE5"},
    {"email": "user14@quizapp.com", "password": "P3paKM9rK$)"},
]

# List of question and option IDs
questions_and_options = [
    (1, 1), (1, 2), (1, 3), (1, 4), (1, 5),
    (2, 6), (2, 7), (2, 8), (2, 9), (2, 10),
    (3, 11), (3, 12), (3, 13), (3, 14), (3, 15),
    (4, 16), (4, 17), (4, 18), (4, 19), (4, 20),
    (5, 21), (5, 22), (5, 23), (5, 24), (5, 25),
    (6, 26), (6, 27), (6, 28), (6, 29), (6, 30),
    (7, 31), (7, 32), (7, 33), (7, 34), (7, 35),
    (8, 36), (8, 37), (8, 38), (8, 39), (8, 40),
    (9, 41), (9, 42), (9, 43), (9, 44), (9, 45),
    (10, 46), (10, 47), (10, 48), (10, 49), (10, 50),
    (11, 51), (11, 52), (11, 53), (11, 54), (11, 55),
    (12, 56), (12, 57), (12, 58), (12, 59), (12, 60),
    (13, 61), (13, 62), (13, 63), (13, 64), (13, 65),
    (14, 66), (14, 67), (14, 68), (14, 69), (14, 70),
    (15, 71), (15, 72), (15, 73), (15, 74), (15, 75),
    (16, 76), (16, 77), (16, 78), (16, 79), (16, 80),
    (17, 81), (17, 82), (17, 83), (17, 84), (17, 85),
    (18, 86), (18, 87), (18, 88), (18, 89), (18, 90),
    (19, 91), (19, 92), (19, 93), (19, 94), (19, 95),
    (20, 96), (20, 97), (20, 98), (20, 99), (20, 100),
    (21, 101), (21, 102), (21, 103), (21, 104), (21, 105),
    (22, 106), (22, 107), (22, 108), (22, 109), (22, 110),
    (23, 111), (23, 112), (23, 113), (23, 114), (23, 115),
    (24, 116), (24, 117), (24, 118), (24, 119), (24, 120),
    (25, 121), (25, 122), (25, 123), (25, 124), (25, 125),
    (26, 126), (26, 127), (26, 128), (26, 129), (26, 130),
    (27, 131), (27, 132), (27, 133), (27, 134), (27, 135),
    (28, 136), (28, 137), (28, 138), (28, 139), (28, 140),
    (29, 141), (29, 142), (29, 143), (29, 144), (29, 145),
    (30, 146), (30, 147), (30, 148), (30, 149), (30, 150),
    (91, 393), (71, 311), (61, 272), (80, 346), (87, 377),
    (75, 327), (83, 361), (84, 362), (79, 343), (86, 372),
    (72, 314), (79, 345), (67, 295), (66, 293), (65, 288),
    (87, 375), (77, 336), (78, 340), (64, 282), (63, 278),
    (88, 379), (82, 356), (68, 298), (67, 297), (62, 275),
    (63, 279), (84, 363), (63, 281), (88, 378), (62, 277),
    (81, 352), (82, 357), (83, 359), (70, 307), (72, 315),
    (89, 384), (68, 299), (89, 382), (85, 368), (74, 323),
    (78, 341), (87, 374), (91, 392), (78, 339), (66, 291),
    (65, 289), (80, 347), (80, 349), (70, 309), (69, 305),
    (71, 313), (74, 325), (91, 390), (73, 321), (90, 386),
    (72, 317), (90, 388), (75, 329), (78, 338), (85, 369),
    (77, 335), (67, 296), (74, 322), (89, 385), (71, 312),
    (73, 319), (77, 337), (86, 370), (88, 380), (68, 301),
    (69, 303), (66, 290), (61, 270), (81, 351), (79, 344),
    (62, 274), (80, 348), (65, 286), (83, 360), (86, 371),
    (61, 271), (89, 383), (69, 302), (87, 376), (61, 273),
    (64, 285), (85, 367), (84, 365), (64, 283), (83, 358),
    (81, 353), (82, 354), (63, 280), (65, 287), (84, 364),
    (82, 355), (64, 284), (79, 342), (86, 373), (85, 366),
    (75, 326), (90, 389), (62, 276), (88, 381), (66, 292),
    (68, 300), (81, 350), (67, 294), (74, 324), (91, 391),
    (73, 320), (73, 318), (90, 387), (71, 310), (72, 316),
    (70, 306), (69, 304), (77, 334), (75, 328), (70, 308)
]

class UserBehavior(SequentialTaskSet):
    
    def on_start(self):
        """ Called when a Locust user starts before any task is scheduled """
        user_data = random.choice(users)  # Pick a random user data dictionary
        # self.user = self.parent.client  # Assuming self.parent has the Locust HttpUser instance
        self.login(user_data)
    
    def login(self, user_data):
        response = self.client.post("/auth/login", json={
            "email": user_data["email"],
            "password": user_data["password"]
        })
        self.token = response.json().get('token')
    
    @task
    def get_test_details(self):
        headers = {'Authorization': f'Bearer {self.token}'}
        self.client.get("/session/test", params={"email": "user3@quizapp.com"}, headers=headers)
    
    @task
    def save_user_answer(self):
        headers = {'Authorization': f'Bearer {self.token}'}
        random.shuffle(questions_and_options)  # Shuffle before iterating
        for question_id, option_id in questions_and_options:
            self.client.put("/answers/saveUserAnswer", params={
                "email": "user3@quizapp.com",
                "examId": "EXM00001",
                "questionId": question_id,
                "optionId": option_id
            }, headers=headers)
    
    @task
    def get_all_user_answers(self):
        headers = {'Authorization': f'Bearer {self.token}'}
        self.client.post("/answers/getAllUserAnswers", json={
            "email": "user3@quizapp.com",
            "examId": "EXM00001"
        }, headers=headers)

class WebsiteUser(HttpUser):
    tasks = [UserBehavior]
    wait_time = between(1, 3)  # wait between 1 and 3 seconds after each task
    host = "http://172.18.61.111:8088/quizapp/api/v1"