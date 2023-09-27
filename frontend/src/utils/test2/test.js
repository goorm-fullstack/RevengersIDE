"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.testFunction = exports.firstFunction = void 0;
const firstFunction = (param1, param2) => {
    return { param1: param1, param2: param2 };
};
exports.firstFunction = firstFunction;
const testFunction = () => {
    console.log((0, exports.firstFunction)("Hi", 1), (0, exports.firstFunction)("Hello", 2));
};
exports.testFunction = testFunction;
