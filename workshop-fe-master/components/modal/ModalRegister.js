import { getDetailUserService } from "@/services/adminService";
import { regEvent } from "@/services/userService";
import Cookies from "js-cookie";
import React, { useState, useEffect } from "react";
import { toast } from "react-toastify";
import { Input } from "antd";
import { FaQuestion } from "react-icons/fa";
import { questionService } from "@/services/tickedService";

const { TextArea } = Input;

const ModalRegister = ({ show, handleClose, eventId }) => {
  const [user, setUser] = useState(null);
  const [userId, setUserId] = useState(Cookies.get("userId"));
  const [question, setQuestion] = useState("");

  const handleRegEvent = async (eventId, userId) => {
    const res = await regEvent(eventId, userId);

    switch (res.data.statusCode) {
      case "200":
        toast.success("Success");
        handleClose();
        break;
      default:
        break;
    }
  };
  const handleQuestionService = async (eventId, userId) => {
    let data = {
      name: userId,
      description: question,
      eventId: eventId,
    };
    const res = await questionService(data);

    switch (res.data.statusCode) {
      case "200":
        setQuestion("");
        // toast.success("Success");
        handleClose();
        break;
      default:
        break;
    }
  };

  const getUser = async (id) => {
    const res = await getDetailUserService(id);
    switch (res.data.statusCode) {
      case "200":
        setUser(res.data.data);
        break;

      default:
        break;
    }
  };
  useEffect(() => {
    if (userId) {
      getUser(userId);
    }
  }, []);

  return (
    <React.Fragment>
      {show && user && (
        <div
          className="fixed inset-0 z-[99999] overflow-y-auto"
          aria-labelledby="modal-title"
          aria-modal="true"
        >
          <div className="flex min-h-screen px-4 pt-4 pb-20 text-center sm:block sm:p-0">
            <div
              className="fixed inset-0 transition-opacity bg-gray-500 bg-opacity-75"
              aria-hidden="true"
              onClick={handleClose}
            ></div>
            <span
              className="hidden sm:inline-block sm:align-middle sm:h-screen"
              aria-hidden="true"
            >
              &#8203;
            </span>
            <div className="relative inline-block bg-white rounded-lg shadow w-full m-auto lg:w-[60%]">
              <div className="flex ">
                <h3 className="pt-3 pl-12 text-lg font-semibold textPrimary">
                  Register Event
                </h3>
                <button
                  type="button"
                  className="text-gray-400 bg-transparent rounded-lg text-sm p-1.5 ml-auto inline-flex items-center"
                  data-modal-toggle="defaultModal"
                >
                  <svg
                    className="w-5 h-5"
                    fill="currentColor"
                    viewBox="0 0 20 20"
                    xmlns="http://www.w3.org/2000/svg"
                    onClick={handleClose}
                  >
                    <path
                      fillRule="evenodd"
                      d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z"
                      clipRule="evenodd"
                    ></path>
                  </svg>
                </button>
              </div>
              <div className="px-12 py-8 ">
                <div className="grid grid-cols-2 gap-4 text-left">
                  <div>
                    <div className=""> ID</div>
                    <input
                      disabled
                      value={user.usersId}
                      className="py-2 px-4 w-full border rounded-lg"
                    />
                  </div>
                  <div>
                    <div className=""> Full Name</div>
                    <input
                      disabled
                      value={user.fullName}
                      className="py-2 px-4 w-full border rounded-lg"
                    />
                  </div>
                  {/* <div>
                    <div className=""> Full Name</div>
                    <input
                      disabled
                      value={user.ma}
                      className="py-2 px-4 w-full border rounded-lg"
                    />
                  </div> */}
                  <div>
                    <div className=""> Email</div>
                    <input
                      disabled
                      value={user.email}
                      className="py-2 px-4 w-full border rounded-lg"
                    />
                  </div>
                  <div>
                    <div className=""> Major</div>
                    <input
                      disabled
                      value={user.majorResponse.name}
                      className="py-2 px-4 w-full border rounded-lg"
                    />
                  </div>
                  <div>
                    <div className="">Course</div>
                    <input
                      disabled
                      value={user.courseResponse.name}
                      className="py-2 px-4 w-full border rounded-lg"
                    />
                  </div>
                  <div>
                    <div className="">Phone</div>
                    <input
                      disabled
                      value={user.phone}
                      className="py-2 px-4 w-full border rounded-lg"
                    />
                  </div>
                  <div>
                    <div className="text-xs font-semibold mb-2">Question</div>
                    <TextArea
                      className="focus:border-[#2D3791] hover:border-[#2D3791]"
                      value={question}
                      onChange={(e) => setQuestion(e.target.value)}
                      rows={4}
                    />
                  </div>
                </div>
                <div className="mt-4 flex justify-end">
                  <button
                    onClick={() => {
                      handleRegEvent(eventId, userId);
                      handleQuestionService(eventId, userId);
                    }}
                    className="flex justify-center items-center bgPrimary text-white border px-4 py-2 rounded "
                  >
                    Register
                  </button>
                </div>
                {/* <div >
                  đăng ký
                </div> */}
              </div>
            </div>
          </div>
        </div>
      )}
    </React.Fragment>
  );
};

export default ModalRegister;
